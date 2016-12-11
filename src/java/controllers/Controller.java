package controllers;

import classes.DAO.OcorrenciaDAOJPA;
import classes.Ocorrencia;
import classes.transaction.object.ResultadoTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

@WebServlet(name = "Controller", urlPatterns = {"/principal.html", "/resultados.html"})
public class Controller extends HttpServlet {

    @PersistenceUnit(unitName= "jpa-web-pu")
    EntityManagerFactory emf;
    
    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;
    
    /**Redirecionamento para a página resultados.html
     * Monta a lista de pontuação por grupos*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getRequestURI().contains("principal.html")){
            request.getRequestDispatcher("WEB-INF/principal.jsp").forward(request, response);
        } else if (request.getRequestURI().contains("resultados.html")) {
            HttpSession session = request.getSession();
            OcorrenciaDAOJPA daoOcorrencia = new OcorrenciaDAOJPA(ut, emf);
            List<ResultadoTO> pontos = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                ResultadoTO resultadoTO = new ResultadoTO();
                resultadoTO.setGrupo(i);
                resultadoTO.setPontos(daoOcorrencia.getPontosPor(new Long(i)));
                pontos.add(resultadoTO);
            }
            session.setAttribute("pontos", pontos);
            
            request.getRequestDispatcher("WEB-INF/resultados.jsp").forward(request, response);
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
