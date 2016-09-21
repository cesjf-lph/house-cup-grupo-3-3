/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Aluno;
import classes.DAO.AlunoDAOJPA;
import classes.DAO.OcorrenciaDAOJPA;
import classes.Ocorrencia;
import classes.Professor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Airton
 */
@WebServlet(name = "AlunoDAOController", urlPatterns = {"/principal.html", "/listar.html", "/novoAluno.html", "/ocorrencia.html"})
public class Controller extends HttpServlet {

    @PersistenceUnit(unitName= "jpa-web-pu")
    EntityManagerFactory emf;
    
    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getRequestURI().contains("listar.html")) {
            List<Aluno> lista = new ArrayList<>();
            try {
                AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
                lista = dao.findAlunoEntities();
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                lista = new ArrayList<Aluno>();
                request.setAttribute("erro", "Problema ao listar os aluno!");
            }
            
            request.setAttribute("alunos", lista);
            request.getRequestDispatcher("/WEB-INF/listar.jsp").forward(request, response);
        } 
        else if (request.getRequestURI().contains("novoAluno.html")){
            request.getRequestDispatcher("/WEB-INF/novoAluno.jsp").forward(request, response);
        } else if (request.getRequestURI().contains("principal.html")){
            request.getRequestDispatcher("WEB-INF/principal.jsp").forward(request, response);
        } else if (request.getRequestURI().contains("ocorrencia.html")){
            //Aqui será populado a lista de professores e alunos
            HttpSession session = request.getSession();
            List<Aluno> alunos = new ArrayList<>();
            List<Professor> professores = new ArrayList<>();
            AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
            alunos = dao.findAlunoEntities();
            session.setAttribute("alunos", alunos);
            request.getRequestDispatcher("WEB-INF/ocorrencia.jsp").forward(request, response);
        } else if (request.getRequestURI().contains("novoProfessor.html")){
            request.getRequestDispatcher("WEB-INF/novoProfessor.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains("novoAluno.html")) {
            Date dataAtual = new Date();
            Aluno aluno = new Aluno();
            aluno.setNomeCompleto(request.getParameter("nomeCompleto"));
            aluno.setGrupo(Integer.parseInt(request.getParameter("grupo")));
            Ocorrencia ocorrencia = new Ocorrencia();
            ocorrencia.setPontos(10);
            try {
                AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
                dao.create(aluno);
                OcorrenciaDAOJPA dao2 = new OcorrenciaDAOJPA(ut, emf);
                dao2.create(ocorrencia);
                
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("listar.html?erro=Erro ao criar o Aluno!");
                return;
            }

            response.sendRedirect("listar.html");
        } 
    }
    


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
