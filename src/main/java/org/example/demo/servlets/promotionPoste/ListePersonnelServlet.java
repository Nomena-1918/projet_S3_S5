package org.example.demo.servlets.promotionPoste;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.Connexion;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.*;
import org.example.demo.models.promotionPoste.SituationProPersonne;
import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Fonction;
import org.example.demo.models.travail.Voyage;
import org.example.demo.models.travail.VoyageEmploye;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
