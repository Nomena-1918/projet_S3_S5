package org.voyage.demo.servlets.composition_voyage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.composition_voyage.Bouquet;
import org.voyage.demo.models.composition_voyage.CategorieLieu;
import org.voyage.demo.models.composition_voyage.TypeDuree;
import org.voyage.demo.models.composition_voyage.Voyage;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "voyageServlet", value = "/voyage-servlet")
public class VoyageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idBouquet=Integer.parseInt(request.getParameter("idBouquet"));
        Integer idCategorieLieu=Integer.parseInt(request.getParameter("idCategorieLieu"));
        Integer idTypeDuree=Integer.parseInt(request.getParameter("idTypeDuree"));
        try(Connection connection = ConnexionPool.getConnection()){
            Voyage voyage=new Voyage(new Bouquet(idBouquet),new TypeDuree(idTypeDuree),new CategorieLieu(idCategorieLieu));

            Voyage.insertVoyage(connection,voyage);
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
        List<Bouquet> listBouquet = Bouquet.readAll(connection);
        request.setAttribute("list-bouquet", listBouquet);

        List<CategorieLieu> listCategorieLieu = CategorieLieu.readAll(connection);
        request.setAttribute("list-categorieLieu",listCategorieLieu);

        List<TypeDuree> listTypeDuree = TypeDuree.readAll(connection);
        request.setAttribute("list-typeDuree",listTypeDuree);

        RequestDispatcher dispatcher = request.getRequestDispatcher("voyage.jsp");
        dispatcher.forward(request, response);
    }
}
