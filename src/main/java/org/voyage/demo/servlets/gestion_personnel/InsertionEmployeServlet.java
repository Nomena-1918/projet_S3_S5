package org.voyage.demo.servlets.gestion_personnel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.gestion_personnel.Genre;
import org.voyage.demo.models.gestion_personnel.Employe;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "insertionEmployeServlet", value = "/insertionEmploye-servlet")
public class InsertionEmployeServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request,response,connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nom=request.getParameter("nom");
        String prenom=request.getParameter("prenom");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dtn = LocalDate.parse(request.getParameter("date"), formatter);

        Integer idSexe=Integer.parseInt(request.getParameter("idsexe"));

        try(Connection connection = ConnexionPool.getConnection()){
            Genre sex=new Genre(idSexe);
            Employe employe=new Employe(nom,prenom,dtn,sex);
            Employe.insertEmploye(connection,employe);
            getInfo(request,response,connection);
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
        List<Genre> sexes= Genre.readAll(connection);
        request.setAttribute("list-sexe", sexes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionEmploye.jsp");
        dispatcher.forward(request, response);
    }

}
