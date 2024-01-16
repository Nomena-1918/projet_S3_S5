package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.*;

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
        Long idActivite=Long.parseLong(request.getParameter("idActivite"));
        Long idBouquet=Long.parseLong(request.getParameter("idBouquet"));
        Long idCategorieLieu=Long.parseLong(request.getParameter("idCategorieLieu"));
        Long idTypeDuree=Long.parseLong(request.getParameter("idTypeDuree"));
        int nombre= Integer.parseInt(request.getParameter("nombre"));
        try(Connection connection = ConnexionPool.getConnection()){
            VoyageActivite voyageActivite=new VoyageActivite();
            voyageActivite.setIdActivite(idActivite);
            voyageActivite.setIdCategorieLieu(idCategorieLieu);
            voyageActivite.setIdTypeDuree(idTypeDuree);
            voyageActivite.setIdBouquet(idBouquet);
            voyageActivite.setNombre(nombre);

            VoyageActivite.insertActiviteBouquet(connection,voyageActivite);

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

        List<Bouquet> listBouquet = Bouquet.readAll(connection);
        request.setAttribute("list-bouquet", listBouquet);

        List<CategorieLieu> listCategorieLieu = CategorieLieu.readAll(connection);
        request.setAttribute("list-categorieLieu",listCategorieLieu);

        List<TypeDuree> listTypeDuree = TypeDuree.readAll(connection);
        request.setAttribute("list-typeDuree",listTypeDuree);

        RequestDispatcher dispatcher = request.getRequestDispatcher("association.jsp");
        dispatcher.forward(request, response);
    }
}
