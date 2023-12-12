package org.example.demo.models;

import org.example.demo.database.Connexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Activite {
    private Long id;
    private String nom;

    public Activite(Long id,String nom){
        this.id=id;
        this.nom=nom;
    }

    public Activite(){

    }

    public void setId(Long id) {this.id = id;}

    public void setNom(String nom) {this.nom = nom;}

    public Long getId() {return id;}
    public String getNom() {return nom;}

    public static void insertActivite(Connection connection,Activite activite) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO activite(nom) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, activite.getNom());

            System.out.println("\n" + query + "\n");

            statement.executeUpdate();
            connection.commit();
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }

        if (new_connex)
            connection.close();

    }

    public static List<Activite> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "SELECT * FROM activite";
        List<Activite> listActivite = new ArrayList<>();
        Activite activite;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    activite = new Activite();
                    activite.setId(resultSet.getLong("id"));
                    activite.setNom(resultSet.getString("nom"));
                    listActivite.add(activite);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listActivite;
    }

    @Override
    public String toString() {
        return String.format("Activite{id=%s, nom='%s'}", getId(), getNom());
    }

}
