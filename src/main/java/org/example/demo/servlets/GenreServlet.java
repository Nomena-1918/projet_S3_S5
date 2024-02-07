package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.connexion.ConnexionPool;
import org.example.demo.models.gestion_personnel.Genre;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "genreServlet", value = "/genre-servlet")
public class GenreServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("genre.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nomActivite=request.getParameter("nom");
        try(Connection connection = ConnexionPool.getConnection()){
            Genre activite = new Genre(nomActivite);
            Genre.insertSexe(connection,activite);
            RequestDispatcher dispatcher = request.getRequestDispatcher("genre.jsp");

            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("genre.jsp");
            dispatcher.forward(request, response);
        }
    }
}
