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
import javax.transaction.UserTransaction;

/**
 *
 * @author Airton
 */
@WebServlet(name = "AlunoDAOController", urlPatterns = {"/listar.html", "/novo.html"})
public class alunoDAOController extends HttpServlet {

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
                lista = dao.findCandidatoEntities();
            } catch (Exception ex) {
                Logger.getLogger(alunoDAOController.class.getName()).log(Level.SEVERE, null, ex);
                lista = new ArrayList<Aluno>();
                request.setAttribute("erro", "Problema ao listar os aluno!");
            }
            
            request.setAttribute("alunos", lista);
            request.getRequestDispatcher("/WEB-INF/listar.jsp").forward(request, response);
        } 
        else if (request.getRequestURI().contains("novo.html"))
            request.getRequestDispatcher("/WEB-INF/novo.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains("novo.html")) {
            Date dataAtual = new Date();
            Aluno aluno = new Aluno();
            aluno.setNomeCompleto(request.getParameter("nomeCompleto"));
            aluno.setIdade(Integer.parseInt(request.getParameter("idade")));
            aluno.setSexo(Boolean.parseBoolean(request.getParameter("sexo")));
            aluno.setGrupo(request.getParameter("grupo"));
            Ocorrencia ocorrencia = new Ocorrencia();
            ocorrencia.setPontos(10);
            try {
                AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
                dao.create(aluno);
                OcorrenciaDAOJPA dao2 = new OcorrenciaDAOJPA(ut, emf);
                dao2.create(ocorrencia);
                
            } catch (Exception ex) {
                Logger.getLogger(alunoDAOController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("listar.html?erro=Erro ao criar o Candidato!");
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
