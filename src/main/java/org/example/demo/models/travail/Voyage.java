package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.*;

import javax.swing.plaf.BorderUIResource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Voyage {
    private Long id;
    private Bouquet bouquet;
    private TypeDuree typeDuree;
    private CategorieLieu categorieLieu;

    public Voyage(Long id, Bouquet bouquet, TypeDuree typeDuree, CategorieLieu categorieLieu) {
        this.id = id;
        this.bouquet = bouquet;
        this.typeDuree = typeDuree;
        this.categorieLieu = categorieLieu;
    }

    public Voyage(Bouquet bouquet, TypeDuree typeDuree, CategorieLieu categorieLieu) {
        this.bouquet = bouquet;
        this.typeDuree = typeDuree;
        this.categorieLieu = categorieLieu;
    }

    public Voyage() {

    }

    public Voyage(Long id) {
        this.id = id;
    }

    public Voyage(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bouquet getBouquet() {
        return bouquet;
    }

    public void setBouquet(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    public TypeDuree getTypeDuree() {
        return typeDuree;
    }

    public void setTypeDuree(TypeDuree typeDuree) {
        this.typeDuree = typeDuree;
    }

    public CategorieLieu getCategorieLieu() {
        return categorieLieu;
    }

    public void setCategorieLieu(CategorieLieu categorieLieu) {
        this.categorieLieu = categorieLieu;
    }

    public static void insertVoyage(Connection connection, Voyage voyage) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO voyage(id_bouquet,id_categorie_lieu,id_duree) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, voyage.getBouquet().getId());
            statement.setLong(2, voyage.getCategorieLieu().getId());
            statement.setLong(3, voyage.getTypeDuree().getId());

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

    public static List<Voyage> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        List<Voyage> listVoyage = new ArrayList<>();
        String query = "select * from vue_voyage_complet";
        Voyage voyage;
          try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    voyage = new Voyage();
                    voyage.setId(resultSet.getLong("id"));
                    voyage.setBouquet(new Bouquet(  resultSet.getLong("id_bouquet"),
                                                    resultSet.getString("nom_bouquet")));
                    voyage.setCategorieLieu(new CategorieLieu(  resultSet.getLong("id_categorie_lieu"),
                                                                resultSet.getString("nom_categorie_lieu")));
                    voyage.setTypeDuree(new TypeDuree(  resultSet.getLong("id_duree"),
                                                        resultSet.getString("nom")));
                    listVoyage.add(voyage);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }

        if (new_connex)
            connection.close();

        return listVoyage;
    }
}
