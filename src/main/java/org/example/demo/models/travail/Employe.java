package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.Bouquet;
import org.example.demo.models.CategorieLieu;
import org.example.demo.models.TypeDuree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employe {
    private Long id;
    private String nom;
    private Fonction fonction;

    public Employe(Fonction fonction) {
        this.fonction = fonction;
    }

    public Employe(Long id) {
        this.id = id;
    }

    public Employe(Long id, String nom, Fonction fonction) {
        this.id = id;
        this.nom = nom;
        this.fonction = fonction;
    }

    public Employe(String nom, Fonction fonction) {
        this.nom = nom;
        this.fonction = fonction;
    }

    public Employe() {

    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }


    public static void insertEmploye(Connection connection, Employe employe) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO employe(nom,id_fonction) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employe.getNom());
            statement.setDouble(2, employe.getFonction().getId());

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
    public static List<Employe> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        List<Employe> listEmploye = new ArrayList<>();
        Fonction fonction;
        Employe employe;
        String query = "SELECT * FROM employe_complet";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    fonction = new Fonction(
                            resultSet.getLong("id_fonction"),
                            resultSet.getString("nom_fonction"),
                            resultSet.getDouble("salaire_horaire")
                    );
                    employe = new Employe(
                            resultSet.getLong("id"),
                            resultSet.getString("nom"),
                            fonction
                    );
                    listEmploye.add(employe);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listEmploye;
    }
}
