package org.example.demo.models.gestion_personnel;

import org.example.demo.connexion.Connexion;
import veda.godao.annotations.*;
import veda.godao.DAO;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Table("vue_liste_personnel")
public class SituationProPersonne {

    static final DAO dao;
    static {
        dao=new DAO(
                Connexion.database,
                Connexion.host,
                Connexion.port,
                Connexion.username,
                Connexion.password,
                Connexion.use_ssl,
                Connexion.SGBD);
    }
    @PrimaryKey
    @Column("id_row")
    private Integer id_row;

    @ForeignKey(recursive=true)
    @Column("id")
    private Employe employe;

    @ForeignKey(recursive=true)
    @Column("id_fonction")
    private Fonction fonction;

    @ForeignKey(recursive=true)
    @Column("id_grade")
    private GradeFonction gradeFonction;

    @Column("derniere_date_embauche")
    private LocalDate dateEmbauche;

    public SituationProPersonne() {
    }



    public SituationProPersonne(Employe employe, Fonction fonction, GradeFonction gradeFonction) {
        this.employe = employe;
        this.fonction = fonction;
        this.gradeFonction = gradeFonction;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public String getDateEmbaucheStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateEmbauche.format(formatter);
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Integer getId_row() {
        return id_row;
    }

    public void setId_row(Integer id_row) {
        this.id_row = id_row;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public GradeFonction getGradeFonction() {
        return gradeFonction;
    }

    public void setGradeFonction(GradeFonction gradeFonction) {
        this.gradeFonction = gradeFonction;
    }

    public static List<SituationProPersonne> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        SituationProPersonne[] clients=dao.select(connection, SituationProPersonne.class);
        if (new_connex)
            connection.close();
        return Arrays.asList(clients);
    }

    public Double getSalaireActuel() {
        return this.gradeFonction.getTauxHoraireCoeff() * this.fonction.getSalaireHoraire();
    }

}
