package org.example.demo.servlets.travail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.ActiviteBouquetPrix;
import org.example.demo.models.CategorieLieu;
import org.example.demo.models.travail.Benefice;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "beneficeServlet", value = "/benefice-servlet")
public class BeneficeServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/Benefice.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Double prixMin=Double.parseDouble(request.getParameter("prixMin"));
        Double prixMax=Double.parseDouble(request.getParameter("prixMax"));
        try(Connection connection = ConnexionPool.getConnection()){
            List<Benefice> benefices=Benefice.getBeneficeBetweenPrix(connection,prixMin,prixMax);
            request.setAttribute("list-benefices",benefices);
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/Benefice.jsp");
            dispatcher.forward(request, response);
        }  catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/Benefice.jsp");
            dispatcher.forward(request, response);
        }
    }
}
