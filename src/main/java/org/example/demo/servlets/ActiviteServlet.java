package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.connexion.ConnexionPool;
import org.example.demo.models.composition_voyage.Activite;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "activiteServlet", value = "/activite-servlet")
public class ActiviteServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("activite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nomActivite=request.getParameter("nom");
        try(Connection connection = ConnexionPool.getConnection()){
            Activite activite = new Activite(nomActivite);
            Activite.insertActivite(connection,activite);
            RequestDispatcher dispatcher = request.getRequestDispatcher("activite.jsp");

            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("activite.jsp");
            dispatcher.forward(request, response);
        }
    }
}
