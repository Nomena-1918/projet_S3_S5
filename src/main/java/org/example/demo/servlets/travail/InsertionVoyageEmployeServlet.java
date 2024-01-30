package org.example.demo.servlets.travail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.*;
import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Voyage;
import org.example.demo.models.travail.VoyageEmploye;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "insertionVoyageEmployeServlet", value = "/insertionVoyageEmploye-servlet")
public class InsertionVoyageEmployeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long idVoyage=Long.parseLong(request.getParameter("idVoyage"));
        Integer idEmploye=Integer.parseInt(request.getParameter("idEmploye"));
        int heureTravail=Integer.parseInt(request.getParameter("heure"));
        try(Connection connection = ConnexionPool.getConnection()){
            Voyage voyage=new Voyage(idVoyage);
            Employe employe=new Employe(idEmploye);

            VoyageEmploye voyageEmploye=new VoyageEmploye(voyage,employe,heureTravail);
            VoyageEmploye.insertVoyageEmploye(connection,voyageEmploye);
            getInfo(request, response, connection);
        } catch (Exception e) {
            try(Connection connection = ConnexionPool.getConnection()){
                request.setAttribute("messageError",e.getMessage());
                getInfo(request, response, connection);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Employe> listEmploye = Employe.readAll(connection);
        request.setAttribute("list-employe", listEmploye);

        List<Voyage> listVoyage = Voyage.readAll(connection);
        request.setAttribute("list-voyage", listVoyage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionVoyageEmploye.jsp");
        dispatcher.forward(request, response);
    }
}
