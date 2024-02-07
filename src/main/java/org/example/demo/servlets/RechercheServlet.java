package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.connexion.ConnexionPool;
import org.example.demo.models.composition_voyage.VoyageActivite;
import org.example.demo.models.composition_voyage.Bouquet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "rechercheServlet", value = "/recherche-servlet")
public class RechercheServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()){
            getInfo(request, response, connection,"recherche.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long idBouquet=Long.parseLong(request.getParameter("bouquet"));
        try(Connection connection = ConnexionPool.getConnection()){
            List<VoyageActivite> list=VoyageActivite.findActiviteBouquet(connection,idBouquet);
            request.setAttribute("list-voyageActivite", list);
            getInfo(request, response, connection,"listeActivitebouquet.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response, Connection connection,String view) throws Exception {
        List<Bouquet> listBouquet = Bouquet.readAll(connection);
        request.setAttribute("list-bouquet", listBouquet);

        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}
