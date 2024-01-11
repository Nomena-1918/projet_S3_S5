package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.Activite;
import org.example.demo.models.ActiviteBouquet;
import org.example.demo.models.Bouquet;
import org.example.demo.models.ResteActivite;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "listeResteBilletActiviteServlet", value = "/listeResteBilletActivite-servlet")
public class ListeResteBilletActiviteServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            List<ResteActivite> resteActivites=ResteActivite.findAll(connection,null);
            request.setAttribute("list-resteActivite", resteActivites);
            getInfo(request, response, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("ListeResteBilletActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long idActivite=Long.parseLong(request.getParameter("idActivite"));
        try(Connection connection = ConnexionPool.getConnection()){
            List<ResteActivite> resteActivites=ResteActivite.findAll(connection,idActivite);
            request.setAttribute("list-resteActivite", resteActivites);
            getInfo(request, response, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("ListeResteBilletActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Activite> listActivite = Activite.readAll(connection);
        request.setAttribute("list-activite", listActivite);
    }
}
