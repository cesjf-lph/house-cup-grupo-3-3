/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Aluno;
import classes.DAO.AlunoDAOJPA;
import classes.DAO.OcorrenciaDAOJPA;
import classes.DAO.ProfessorDAOJPA;
import classes.Ocorrencia;
import classes.Professor;
import classes.transaction.object.OcorrenciaTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "OcorrenciaController", urlPatterns = {"/ocorrencia.html", "/ocorrencias.html"})
public class OcorrenciaController extends HttpServlet {

    @PersistenceUnit(unitName= "jpa-web-pu")
    EntityManagerFactory emf;
    
    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getRequestURI().contains("ocorrencia.html")){
            //Aqui será populado a lista de professores e alunos
            HttpSession session = request.getSession();
            List<Aluno> alunos = new ArrayList<>();
            List<Professor> professores = new ArrayList<>();
            AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
            alunos = dao.findAlunoEntities();
            ProfessorDAOJPA professorDAOJPA = new ProfessorDAOJPA(ut, emf);
            professores = professorDAOJPA.findProfessorEntities();
            session.setAttribute("alunos", alunos);
            session.setAttribute("professores", professores);
            request.getRequestDispatcher("WEB-INF/ocorrencia.jsp").forward(request, response);
        } else if (request.getRequestURI().contains("ocorrencias.html")) {
            List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
            try {
                OcorrenciaDAOJPA daoOcorrencia = new OcorrenciaDAOJPA(ut, emf);
                ocorrencias = daoOcorrencia.findOcorrenciaEntities();
            } catch (Exception ex) {
                Logger.getLogger(ListarAlunosController.class.getName()).log(Level.SEVERE, null, ex);
                ocorrencias = new ArrayList<Ocorrencia>();
                request.setAttribute("erro", "Problema ao listar ocorrências!");
            }
            AlunoDAOJPA alunoDao = new AlunoDAOJPA(ut, emf);
            ProfessorDAOJPA professorDao = new ProfessorDAOJPA(ut, emf);
            List<OcorrenciaTO> ocorrenciasTO = new ArrayList<OcorrenciaTO>();
            for(Ocorrencia ocorrencia : ocorrencias){
                OcorrenciaTO ocorrenciaTO = new OcorrenciaTO();
                Long idAluno = ocorrencia.getAluno().getId();
                Long idProfessor = ocorrencia.getProfessor().getId();
                Aluno aluno  = alunoDao.findAluno(idAluno);
                Professor professor  = professorDao.findProfessor(idProfessor);
                ocorrenciaTO.setNomeProfessor(professor.getNomeProfessor());
                ocorrenciaTO.setNomeAluno(aluno.getNomeCompleto());
                ocorrenciaTO.setGrupo(aluno.getGrupo());
                ocorrenciaTO.setPontos(String.valueOf(ocorrencia.getPontos()));
                String data = new SimpleDateFormat("dd-MM-yyyy").format(ocorrencia.getDescricao());
                ocorrenciaTO.setData(data);
                ocorrenciasTO.add(ocorrenciaTO);
            }
            request.setAttribute("ocorrencias", ocorrenciasTO);
            request.getRequestDispatcher("WEB-INF/ocorrencias.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains("ocorrencia.html")) {
            Date dataAtual = new Date();
            Ocorrencia ocorrencia = new Ocorrencia();
           
            try {
                AlunoDAOJPA daoAluno = new AlunoDAOJPA(ut, emf);
                Long idAluno = Long.parseLong(request.getParameter("aluno"));
                Aluno aluno  = daoAluno.findAluno(idAluno);
                ProfessorDAOJPA daoProfessor = new ProfessorDAOJPA(ut, emf);
                Long idProfessor = Long.parseLong(request.getParameter("professor"));
                int pontos = Integer.parseInt(request.getParameter("pontos"));
                Professor professor  = daoProfessor.findProfessor(idProfessor);
                ocorrencia.setAluno(aluno);
                ocorrencia.setProfessor(professor);
                ocorrencia.setDescricao(dataAtual);
                ocorrencia.setPontos(pontos);
                OcorrenciaDAOJPA daoOcorrencia = new OcorrenciaDAOJPA(ut, emf);
                daoOcorrencia.create(ocorrencia);
                
            } catch (Exception ex) {
                Logger.getLogger(OcorrenciaController.class.getName()).log(Level.SEVERE, null, ex);
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
