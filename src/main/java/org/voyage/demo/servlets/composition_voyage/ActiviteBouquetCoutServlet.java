package org.voyage.demo.servlets.composition_voyage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.composition_voyage.ActiviteBouquetPrix;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "activitebouquetprixServlet", value = "/activitebouquetprix-servlet")
public class ActiviteBouquetCoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("ActiviteVoyageCout.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Double prixMin=Double.parseDouble(request.getParameter("prixMin"));
        Double prixMax=Double.parseDouble(request.getParameter("prixMax"));
        try(Connection connection = ConnexionPool.getConnection()){
            List<ActiviteBouquetPrix> listActiviteBouquet=ActiviteBouquetPrix.getVoyageBetweenPrix(connection,prixMin,prixMax);
            request.setAttribute("list-activitebouquetprix",listActiviteBouquet);
            RequestDispatcher dispatcher = request.getRequestDispatcher("ActiviteVoyageCout.jsp");
            dispatcher.forward(request, response);
        }  catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("ActiviteVoyageCout.jsp");
            dispatcher.forward(request, response);
        }
    }

}
