package controllers;

import classes.DAO.ProfessorDAOJPA;
import classes.Professor;
import java.io.IOException;
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

@WebServlet(name = "ProfessorDAOController", urlPatterns = {"/novoProfessor.html"})
public class ProfessorController extends HttpServlet {

    @PersistenceUnit(unitName= "jpa-web-pu")
    EntityManagerFactory emf;
    
    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getRequestURI().contains("novoProfessor.html")){
            request.getRequestDispatcher("WEB-INF/novoProfessor.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains("novoProfessor.html")) {
            Professor professor = new Professor();
            professor.setNomeProfessor(request.getParameter("nomeProfessor"));
            try {
                ProfessorDAOJPA dao = new ProfessorDAOJPA(ut, emf);
                dao.create(professor);
                
            } catch (Exception ex) {
                Logger.getLogger(ProfessorController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("principal.html?erro=Erro ao criar o Professor!");
                return;
            }

            response.sendRedirect("principal.html");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
