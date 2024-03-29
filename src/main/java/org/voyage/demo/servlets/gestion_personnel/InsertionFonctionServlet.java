package org.voyage.demo.servlets.gestion_personnel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.gestion_personnel.Fonction;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "insertionFonctionServlet", value = "/insertionFonction-servlet")
public class InsertionFonctionServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionFonction.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nomFonction=request.getParameter("nomFonction");
        Double salaireHoraire=Double.parseDouble(request.getParameter("salaireHoraire"));
        try(Connection connection = ConnexionPool.getConnection()){
            Fonction fonction= new Fonction(nomFonction,salaireHoraire);
            Fonction.insertFonction(connection,fonction);
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionFonction.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionFonction.jsp");
            dispatcher.forward(request, response);
        }
    }

}
