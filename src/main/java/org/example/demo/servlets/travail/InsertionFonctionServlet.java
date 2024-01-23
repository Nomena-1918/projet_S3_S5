package org.example.demo.servlets.travail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.Bouquet;
import org.example.demo.models.travail.Fonction;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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
