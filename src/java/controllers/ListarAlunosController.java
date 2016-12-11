package controllers;

import classes.Aluno;
import classes.DAO.AlunoDAOJPA;
import classes.DAO.OcorrenciaDAOJPA;
import classes.Ocorrencia;
import classes.transaction.object.AlunoTO;
import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet(name = "listarAlunosController", urlPatterns = {"/listar.html"})
public class ListarAlunosController extends HttpServlet {

    @PersistenceUnit(unitName= "jpa-web-pu")
    EntityManagerFactory emf;
    
    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;
    
    /**Método para redirecionamento para a página listar.html
     *Montagem e população das listas de alunos e pontuações, por meio das ocorrências dos mesmos.*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getRequestURI().contains("listar.html")) {
            String idGrupo = request.getParameter("id");
            if (idGrupo != null) {
                List<Aluno> lista = new ArrayList<>();
                List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
                try {
                    AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
                    lista = dao.findAlunoEntities();
                    OcorrenciaDAOJPA daoOcorrencia = new OcorrenciaDAOJPA(ut, emf);
                    ocorrencias = daoOcorrencia.findOcorrenciaEntities();
                    lista = dao.findAlunoEntities();
                } catch (Exception ex) {
                    Logger.getLogger(ListarAlunosController.class.getName()).log(Level.SEVERE, null, ex);
                    lista = new ArrayList<Aluno>();
                    request.setAttribute("erro", "Problema ao listar os aluno!");
                }
                int pontuacaoTotalGrupo = 0;
                List<AlunoTO> alunos = new ArrayList<AlunoTO>();
                for (Aluno aluno : lista) {
                    int pontuacaoTotalPorAluno = 0;
                    for (Ocorrencia ocorrencia : ocorrencias) {
                        if (aluno.getId() == ocorrencia.getAluno().getId()
                                && String.valueOf(aluno.getGrupo()).equals(idGrupo)) {
                            pontuacaoTotalPorAluno+= ocorrencia.getPontos();
                        }
                    }
                    pontuacaoTotalGrupo+=pontuacaoTotalPorAluno;
                    AlunoTO alunoTO = new  AlunoTO();
                    alunoTO.setGrupo(aluno.getGrupo());
                    alunoTO.setNome(aluno.getNomeCompleto());
                    alunoTO.setPontos(String.valueOf(pontuacaoTotalPorAluno));
                    if (String.valueOf(aluno.getGrupo()).equals(idGrupo)) {
                        alunos.add(alunoTO);
                    }                    
                }
                request.setAttribute("totalPontos", pontuacaoTotalGrupo);
                request.setAttribute("idGrupo", idGrupo);
                request.setAttribute("alunos", alunos);
                request.getRequestDispatcher("/WEB-INF/listarGrupos.jsp").forward(request, response);
            } else {
                List<Aluno> lista = new ArrayList<>();
                List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
                try {
                    AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
                    lista = dao.findAlunoEntities();
                    OcorrenciaDAOJPA daoOcorrencia = new OcorrenciaDAOJPA(ut, emf);
                    ocorrencias = daoOcorrencia.findOcorrenciaEntities();
                    lista = dao.findAlunoEntities();
                } catch (Exception ex) {
                    Logger.getLogger(ListarAlunosController.class.getName()).log(Level.SEVERE, null, ex);
                    lista = new ArrayList<Aluno>();
                    request.setAttribute("erro", "Problema ao listar os aluno!");
                }

                List<AlunoTO> alunos = new ArrayList<AlunoTO>();
                for (Aluno aluno : lista) {
                    int pontuacaoTotal = 0;
                    for (Ocorrencia ocorrencia : ocorrencias) {
                        if (aluno.getId() == ocorrencia.getAluno().getId()) {
                            pontuacaoTotal+= ocorrencia.getPontos();
                        }
                    }
                    AlunoTO alunoTO = new  AlunoTO();
                    alunoTO.setGrupo(aluno.getGrupo());
                    alunoTO.setNome(aluno.getNomeCompleto());
                    alunoTO.setPontos(String.valueOf(pontuacaoTotal));
                    alunos.add(alunoTO);
                }
                request.setAttribute("alunos", alunos);
                request.getRequestDispatcher("/WEB-INF/listar.jsp").forward(request, response);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
