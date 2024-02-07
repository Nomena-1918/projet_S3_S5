package org.example.demo.servlets.promotionPoste;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.connexion.ConnexionPool;
import org.example.demo.models.gestion_personnel.SituationProPersonne;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "listePersonnelServlet", value = "/listePersonnel-servlet")
public class ListePersonnelServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            var list = SituationProPersonne.readAll(connection);
            request.setAttribute("liste-personnel", list);
            RequestDispatcher dispatcher = request.getRequestDispatcher("promotionPoste/ListePersonnel.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
