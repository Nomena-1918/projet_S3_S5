package org.voyage.demo.servlets.composition_voyage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.composition_voyage.Activite;
import org.voyage.demo.models.composition_voyage.Voyage;
import org.voyage.demo.models.composition_voyage.VoyageActivite;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "voyageActiviteServlet", value = "/voyageActivite-servlet")
public class VoyageActiviteServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idActivite=Integer.parseInt(request.getParameter("idActivite"));
        Integer idVoyage=Integer.parseInt(request.getParameter("idVoyage"));
        int nombre= Integer.parseInt(request.getParameter("nombre"));
        try(Connection connection = ConnexionPool.getConnection()){
            VoyageActivite voyageActivite=new VoyageActivite(
                    new Activite(idActivite),
                    new Voyage(idVoyage),
                    nombre
            );
            VoyageActivite.insertVoyageActivite(connection,voyageActivite);
            connection.commit();
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
        List<Activite> listActivite = Activite.readAll(connection);
        request.setAttribute("list-activite", listActivite);

        List<Voyage> listVoyage = Voyage.readAll(connection);
        request.setAttribute("list-voyage", listVoyage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("association.jsp");
        dispatcher.forward(request, response);
    }
}
