package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.*;
import org.example.demo.models.travail.Voyage;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "reservationResteBilletActiviteServlet", value = "/reservationResteBilletActivite-servlet")
public class ReservationVoyageServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long idVoyage=Long.parseLong(request.getParameter("idVoyage"));
        int nombreBillet= Integer.parseInt(request.getParameter("nombrePersonne"));
        try(Connection connection = ConnexionPool.getConnection()){
            ReservationVoyage reservationVoyage=new ReservationVoyage(new Voyage(idVoyage),nombreBillet);
            ReservationVoyage.insertReservationVoyage(connection,reservationVoyage);
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

    private void getInfo(HttpServletRequest request, HttpServletResponse
            response, Connection connection) throws Exception {
        List<Voyage> listVoyage = Voyage.readAll(connection);
        request.setAttribute("list-voyage", listVoyage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("ReservationVoyage.jsp");
        dispatcher.forward(request, response);
    }
}
