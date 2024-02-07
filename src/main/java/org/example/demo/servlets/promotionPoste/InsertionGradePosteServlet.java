package org.example.demo.servlets.promotionPoste;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.connexion.ConnexionPool;
import org.example.demo.models.gestion_personnel.GradeFonction;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "insertionGradePosteServlet", value = "/insertionGradePoste-servlet")
public class InsertionGradePosteServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("promotionPoste/InsertionGradePoste.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nom =request.getParameter("nom");
        double coeff = Double.parseDouble(request.getParameter("coeff"));
        int debutJour=Integer.parseInt(request.getParameter("debutJour"));
        int finJour=Integer.parseInt(request.getParameter("finJour"));
        try(Connection connection = ConnexionPool.getConnection()){
            GradeFonction gf = new GradeFonction(nom, coeff, debutJour, finJour);
            GradeFonction.insertGradeFonction(connection, gf);
            RequestDispatcher dispatcher = request.getRequestDispatcher("promotionPoste/InsertionGradePoste.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("promotionPoste/InsertionGradePoste.jsp");
            dispatcher.forward(request, response);
        }
    }
}
