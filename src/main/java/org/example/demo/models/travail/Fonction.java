package org.example.demo.models.travail;


import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Fonction {
    private Long id;
    private String nom;
    private Double salaireHoraire;

    public Fonction(String nom, Double salaireHoraire) {
        this.nom = nom;
        this.salaireHoraire = salaireHoraire;
    }

    public Fonction(Long id, String nom, Double salaireHoraire) {
        this.id = id;
        this.nom = nom;
        this.salaireHoraire = salaireHoraire;
    }

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

    public Double getSalaireHoraire() {
        return salaireHoraire;
    }

    public void setSalaireHoraire(Double salaireHoraire) {
        this.salaireHoraire = salaireHoraire;
    }


    public static List<Fonction> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
//      Test
        List<Fonction> listFonction = new ArrayList<>();
        Fonction f1=new Fonction(1,"conducteur",123454.0);
        Fonction f2=new Fonction(2,"gardien",10000000.0);
        Fonction f3=new Fonction(3,"cuisinier",200000.0);
        Fonction f4=new Fonction(4,"guide",3000000.0);

        listFonction.add(f1);
        listFonction.add(f2);
        listFonction.add(f3);
        listFonction.add(f4);

//        String query = "SELECT * FROM voyage";
//        Voyage voyage;
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            System.out.println("\n"+query+"\n");
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    voyage = new Voyage();
//                    voyage.setId(resultSet.getLong("id"));
//                    listActivite.add(voyage);
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }

        if (new_connex)
            connection.close();

        return listFonction;
    }
}
