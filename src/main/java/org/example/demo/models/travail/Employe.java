package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.Bouquet;
import org.example.demo.models.CategorieLieu;
import org.example.demo.models.TypeDuree;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Employe {
    private Long id;
    private String nom;
    private Fonction fonction;

    public Employe(Fonction fonction) {
        this.fonction = fonction;
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

    public static List<Employe> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
//      Test
        List<Employe> listEmploye = new ArrayList<>();
        Fonction f1=new Fonction("conducteur",123454.0);
        Fonction f2=new Fonction("gardien",10000000.0);
        Fonction f3=new Fonction("cuisinier",200000.0);
        Fonction f4=new Fonction("guide",3000000.0);

        Employe e1=new Employe(1L,"Antema",f1);
        Employe e2=new Employe(2L,"Nomena",f2);
        Employe e3=new Employe(3L,"Kami",f3);
        Employe e4=new Employe(4L,"Elito",f4);
        listEmploye.add(e1);
        listEmploye.add(e2);
        listEmploye.add(e3);
        listEmploye.add(e4);

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

        return listEmploye;
    }
}
