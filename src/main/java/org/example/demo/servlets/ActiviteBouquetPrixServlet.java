package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "activitebouquetprixServlet", value = "/activitebouquetprix-servlet")
public class ActiviteBouquetPrixServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("ActiviteVoyagePrix.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Double prixMin=Double.parseDouble(request.getParameter("prixMin"));
        Double prixMax=Double.parseDouble(request.getParameter("prixMax"));
        try(Connection connection = ConnexionPool.getConnection()){
            List<ActiviteBouquetPrix> listActiviteBouquet=ActiviteBouquetPrix.getVoyageBetweenPrix(connection,prixMin,prixMax);

            request.setAttribute("list-activitebouquetprix",listActiviteBouquet);
            RequestDispatcher dispatcher = request.getRequestDispatcher("ActiviteVoyagePrix.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
