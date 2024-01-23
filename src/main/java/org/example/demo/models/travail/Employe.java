package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.Bouquet;
import org.example.demo.models.CategorieLieu;
import org.example.demo.models.TypeDuree;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employe {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dtn;
    private String sexe;

    public Employe() {

    }

    public Employe(Long id) {
        this.id = id;
    }

    public Employe(String nom, String prenom, LocalDate dtn, String sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.dtn = dtn;
        this.sexe = sexe;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDtn() {
        return dtn;
    }

    public void setDtn(LocalDate dtn) {
        this.dtn = dtn;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
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


//    public static void insertEmploye(Connection connection, Employe employe) throws Exception {
//        boolean new_connex = false;
//        if (connection == null) {
//            connection = Connexion.getConnexionPostgreSql();
//            new_connex = true;
//        }
//        String query = "INSERT INTO employe(nom) VALUES (?)";
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, employe.getNom());
//
//            System.out.println("\n" + query + "\n");
//
//            statement.executeUpdate();
//            connection.commit();
//        }
//        catch (Exception e) {
//            connection.rollback();
//            throw e;
//        }
//
//        if (new_connex)
//            connection.close();
//    }

//    public static List<Employe> readAll(Connection connection) throws Exception {
//        boolean new_connex = false;
//        if(connection == null) {
//            connection = Connexion.getConnexionPostgreSql();
//            new_connex = true;
//        }
//        List<Employe> listEmploye = new ArrayList<>();
//        Fonction fonction;
//        Employe employe;
//        String query = "SELECT * FROM employe_complet";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            System.out.println("\n"+query+"\n");
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    fonction = new Fonction(
//                            resultSet.getLong("id_fonction"),
//                            resultSet.getString("nom_fonction"),
//                            resultSet.getDouble("salaire_horaire")
//                    );
//                    employe = new Employe(
//                            resultSet.getLong("id"),
//                            resultSet.getString("nom")
//                    );
//                    listEmploye.add(employe);
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (new_connex)
//            connection.close();
//
//        return listEmploye;
//    }
}
