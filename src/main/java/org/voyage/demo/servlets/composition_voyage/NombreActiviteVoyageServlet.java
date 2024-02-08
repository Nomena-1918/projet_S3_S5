package org.voyage.demo.servlets.composition_voyage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.composition_voyage.Activite;
import org.voyage.demo.models.composition_voyage.VoyageActivite;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "nombreActiviteVoyageServlet", value = "/nombreactivitevoyage-servlet")
public class NombreActiviteVoyageServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("NombreActiviteVoyage.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long idActivite=Long.parseLong(request.getParameter("idActivite"));
        try(Connection connection = ConnexionPool.getConnection()){
            List<VoyageActivite> listActiviteBouquet=VoyageActivite.getByActivite(connection,idActivite);
            request.setAttribute("list-voyageActivite",listActiviteBouquet);
            getInfo(request, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("NombreActiviteVoyage.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getInfo(HttpServletRequest request, Connection connection) throws Exception {
        List<Activite> listActivite = Activite.readAll(connection);
        request.setAttribute("list-activite", listActivite);
    }
}
