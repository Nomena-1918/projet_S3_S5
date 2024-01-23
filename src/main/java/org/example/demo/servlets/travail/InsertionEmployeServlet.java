package org.example.demo.servlets.travail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.Activite;
import org.example.demo.models.EntreeActivite;
import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Fonction;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "insertionEmployeServlet", value = "/insertionEmploye-servlet")
public class InsertionEmployeServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionEmploye.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nom=request.getParameter("nom");
        Long idFonction=Long.parseLong(request.getParameter("idFonction"));
        try(Connection connection = ConnexionPool.getConnection()){
            Employe employe=new Employe(nom,new Fonction(idFonction));
            Employe.insertEmploye(connection,employe);
            getInfo(request, response, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionEmploye.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            try(Connection connection = ConnexionPool.getConnection()){
                getInfo(request, response, connection);
            } catch (Exception ex){
                ex.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionEmploye.jsp");
            dispatcher.forward(request, response);
            throw new RuntimeException(e);
        }
    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Fonction> listFonction = Fonction.readAll(connection);
        request.setAttribute("list-fonction", listFonction);
    }
}
