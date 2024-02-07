package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.connexion.ConnexionPool;
import org.example.demo.models.composition_voyage.TypeDuree;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "typedureeServlet", value = "/typeduree-servlet")
public class TypeDureeServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("typeDuree.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nomTypeDuree=request.getParameter("nom");
        int debutJour=Integer.parseInt(request.getParameter("debutJour"));
        int finJour=Integer.parseInt(request.getParameter("finJour"));
        try(Connection connection = ConnexionPool.getConnection()){
            TypeDuree typeDuree = new TypeDuree(nomTypeDuree,debutJour,finJour);
            TypeDuree.insertTypeDuree(connection,typeDuree);
            RequestDispatcher dispatcher = request.getRequestDispatcher("typeDuree.jsp");

            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("typeDuree.jsp");
            dispatcher.forward(request, response);
        }
    }
}
