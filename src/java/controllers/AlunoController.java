package controllers;

import classes.Aluno;
import classes.DAO.AlunoDAOJPA;
import java.io.IOException;
import java.util.Date;
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

@WebServlet(name = "AlunoDAOController", urlPatterns = {"/novoAluno.html"})
public class AlunoController extends HttpServlet {

    @PersistenceUnit(unitName= "jpa-web-pu")
    EntityManagerFactory emf;
    
    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;
    
    /**Redirecionamento para a página novoAluno.html*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       if (request.getRequestURI().contains("novoAluno.html")){
            request.getRequestDispatcher("/WEB-INF/novoAluno.jsp").forward(request, response);
        }
    }
    
    /**Envia através da página novoAluno.html os dados para registro do aluno em banco de dados
     *nomeCompleto
     *grupo*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains("novoAluno.html")) {
            Date dataAtual = new Date();
            Aluno aluno = new Aluno();
            aluno.setNomeCompleto(request.getParameter("nomeCompleto"));
            aluno.setGrupo(Integer.parseInt(request.getParameter("grupo")));
            
            try {
                AlunoDAOJPA dao = new AlunoDAOJPA(ut, emf);
                dao.create(aluno);                
            } catch (Exception ex) {
                Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
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
