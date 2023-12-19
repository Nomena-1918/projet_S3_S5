package org.example.demo.models;

import org.example.demo.database.Connexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bouquet {
    private Long id;
    private String nom;

    public Bouquet(Long id,String nom){
        this.id=id;
        this.nom=nom;
    }

    public Bouquet(){

    }

    public void setId(Long id) {this.id = id;}

    public void setNom(String nom) {this.nom = nom;}

    public Long getId() {return id;}

    public String getNom() {return nom;}

    public static void insertBouquet(Connection connection,Bouquet bouquet) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO Bouquet(nom) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bouquet.getNom());

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

    public static List<Bouquet> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "SELECT * FROM bouquet";
        List<Bouquet> listBouquet = new ArrayList<>();
        Bouquet bouquet;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bouquet = new Bouquet();
                    bouquet.setId(resultSet.getLong("id"));
                    bouquet.setNom(resultSet.getString("nom"));
                    listBouquet.add(bouquet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listBouquet;
    }

    @Override
    public String toString() {
        return String.format("Bouquet{id=%s, nom='%s'}", getId(), getNom());
    }
}
