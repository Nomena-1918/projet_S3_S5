package org.voyage.demo.models.composition_voyage;

import org.voyage.demo.connexion.Connexion;
import veda.godao.annotations.Column;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Table("categorie_lieu")
public class CategorieLieu {
    @PrimaryKey
    @Column("id")
    Integer id;
    @Column("nom")
    String nom;


    public Integer getId() {
        return id;
    }

    public CategorieLieu setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public CategorieLieu(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public CategorieLieu(Integer id) {
        this.id = id;
    }

    public CategorieLieu() {
    }

    public static void insertCategorieLieu(Connection connection, CategorieLieu categorieLieu) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO categorie_lieu(nom) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, categorieLieu.getNom());

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

    public static List<CategorieLieu> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "SELECT * FROM categorie_lieu";
        List<CategorieLieu> listCategorieLieu = new ArrayList<>();
        CategorieLieu categorieLieu;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    categorieLieu = new CategorieLieu();
                    categorieLieu.setId(resultSet.getInt("id"));
                    categorieLieu.setNom(resultSet.getString("nom"));
                    listCategorieLieu.add(categorieLieu);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listCategorieLieu;
    }

    @Override
    public String toString() {
        return String.format("CategorieLieu{id=%s, nom='%s'}", getId(), getNom());
    }

}
