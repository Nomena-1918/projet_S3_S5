package org.example.demo.servlets.travail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.Activite;
import org.example.demo.models.EntreeActivite;
import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Fonction;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "insertionEmployeServlet", value = "/insertionEmploye-servlet")
public class InsertionEmployeServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionEmploye.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nom=request.getParameter("nom");
        String prenom=request.getParameter("prenom");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dtn = LocalDate.parse(request.getParameter("date"), formatter);

        String sexe=request.getParameter("sexe");

        try(Connection connection = ConnexionPool.getConnection()){
            Employe employe=new Employe(nom,prenom,dtn,sexe);
//          Employe.insertEmploye(connection,employe
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionEmploye.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionEmploye.jsp");
            dispatcher.forward(request, response);
        }
    }

}
