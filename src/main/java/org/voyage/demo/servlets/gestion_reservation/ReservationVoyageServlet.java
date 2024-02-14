package org.voyage.demo.servlets.gestion_reservation;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.gestion_reservation.Client;
import org.voyage.demo.models.composition_voyage.Voyage;
import org.voyage.demo.models.gestion_reservation.ReservationVoyage;

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
        Integer idVoyage=Integer.parseInt(request.getParameter("idVoyage"));
        Integer idClient=Integer.parseInt(request.getParameter("idClient"));
        int nombreBillet= Integer.parseInt(request.getParameter("nombrePersonne"));

        try(Connection connection = ConnexionPool.getConnection()){
            ReservationVoyage reservationVoyage=new ReservationVoyage(nombreBillet,new Voyage(idVoyage),new Client(idClient));
            ReservationVoyage.insertReservationVoyage(connection,reservationVoyage);
            getInfo(request, response, connection);
        }
        catch (Exception e) {
            try(Connection connection = ConnexionPool.getConnection()){
                request.setAttribute("messageError",e.getMessage());
                getInfo(request, response, connection);
            } catch (Exception ex) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Voyage> listVoyage = Voyage.readAll(connection);
        request.setAttribute("list-voyage", listVoyage);

        var listClient = Client.readAll(connection);
        request.setAttribute("list-client", listClient);

        RequestDispatcher dispatcher = request.getRequestDispatcher("ReservationVoyage.jsp");
        dispatcher.forward(request, response);
    }
}
