package org.example.demo.models.promotionPoste;

import org.example.demo.database.Connexion;
import org.example.demo.models.client.Client;
import veda.godao.annotations.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

import static org.example.demo.models.promotionPoste.SituationProPersonne.dao;

@Table("vue_grade_fonction")
public class GradeFonction {
    @PrimaryKey
    @Column("id")
    private Integer id;
    @Column("nom")
    private String nom;
    @Column("coeff_taux_horaire")
    private Double tauxHoraireCoeff;
    @Column("debut_ancien")
    private Integer debutAncien;
    @Column("fin_ancien")
    private Integer finAncien;

    public GradeFonction() {
    }

    public GradeFonction(String nom, Double tauxHoraireCoeff, int debutAncien, int finAncien) {
        this.nom = nom;
        this.tauxHoraireCoeff = tauxHoraireCoeff;
        this.debutAncien = debutAncien;
        this.finAncien = finAncien;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void insertGradeFonction(Connection connection, GradeFonction gradeFonction) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "insert into grade_fonction(nom, coeff_taux_horaire, plage_anciennete) values (?, ?, int4range(?, ?))";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, gradeFonction.getNom());
            statement.setDouble(2,gradeFonction.getTauxHoraireCoeff());
            statement.setInt(3,gradeFonction.getDebutAncien());
            statement.setInt(4,gradeFonction.getFinAncien());
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

    public static List<GradeFonction> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        GradeFonction[] gradeFonctions=dao.select(connection,GradeFonction.class);

        if (new_connex)
            connection.close();
        return Arrays.asList(gradeFonctions);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getTauxHoraireCoeff() {
        return tauxHoraireCoeff;
    }

    public void setTauxHoraireCoeff(Double tauxHoraireCoeff) {
        this.tauxHoraireCoeff = tauxHoraireCoeff;
    }

    public Integer getDebutAncien() {
        return debutAncien;
    }

    public void setDebutAncien(Integer debutAncien) {
        this.debutAncien = debutAncien;
    }

    public Integer getFinAncien() {
        return finAncien;
    }

    public void setFinAncien(Integer finAncien) {
        this.finAncien = finAncien;
    }
}
