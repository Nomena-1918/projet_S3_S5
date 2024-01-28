package org.example.demo.models;

import org.example.demo.database.Connexion;
import veda.godao.annotations.Column;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Table("type_duree")
public class TypeDuree {
    @PrimaryKey
    @Column("id")
    Long id;
    @Column("nom")
    String nom;

    int debutJour;
    int finJour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeDuree(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public TypeDuree(String nom, int debutJour, int finJour) {
        this.nom = nom;
        this.debutJour = debutJour;
        this.finJour = finJour;
    }

    public int getDebutJour() {
        return debutJour;
    }

    public void setDebutJour(int debutJour) {
        this.debutJour = debutJour;
    }

    public int getFinJour() {
        return finJour;
    }

    public void setFinJour(int finJour) {
        this.finJour = finJour;
    }

    public TypeDuree() {
    }

    public TypeDuree(Long id) {
        this.id = id;
    }

    public TypeDuree(Long id, String nom, int debutJour, int finJour) {
        this.id = id;
        this.nom = nom;
        this.debutJour = debutJour;
        this.finJour = finJour;
    }

    public static void insertTypeDuree(Connection connection, TypeDuree typeDuree) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO type_duree (nom, intervaljour) VALUES (?, int4range(?, ?))";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, typeDuree.getNom());
            statement.setInt(2,typeDuree.getDebutJour());
            statement.setInt(3,typeDuree.getFinJour());
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

    public static List<TypeDuree> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "SELECT * FROM type_duree";
        List<TypeDuree> listActivite = new ArrayList<>();
        TypeDuree typeDuree;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    typeDuree = new TypeDuree();
                    typeDuree.setId(resultSet.getLong("id"));
                    typeDuree.setNom(resultSet.getString("nom"));
                    listActivite.add(typeDuree);
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
        return String.format("TypeDuree{id=%s, nom='%s'}", getId(), getNom());
    }

}
