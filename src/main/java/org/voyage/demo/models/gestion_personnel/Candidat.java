package org.voyage.demo.models.gestion_personnel;

import org.voyage.demo.connexion.Connexion;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Table("candidat")
public class Candidat {
    private static final DAO dao;
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
    @Column("id")
    private Integer id;
    @Column("nom")
    private String nom;
    @Column("prenom")
    private String prenom;
    @Column("dtn")
    private LocalDate dtn;
    @ForeignKey(recursive = true)
    @Column("id_sexe")
    private Genre genre;

    public Candidat() {

    }

    public Genre getSexe() {
        return genre;
    }

    public void setSexe(Genre genre) {
        this.genre = genre;
    }

    public Candidat(String nom, String prenom, LocalDate dtn, Genre genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.dtn = dtn;
        this.genre = genre;
    }

    public Candidat(Integer id) {
        this.id = id;
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


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void insertEmploye(Connection connection, Candidat candidat) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        try{
            dao.insertWithoutPrimaryKey(connection, candidat);
            connection.commit();
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }

        if (new_connex)
            connection.close();
    }
    public static List<Candidat> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }

        SituationProPersonne[] employes=dao.select(connection,SituationProPersonne.class);

        List<Candidat> emp = new ArrayList<>();
        for (SituationProPersonne s : employes) {
            emp.add(s.getEmploye());
        }

        if (new_connex)
            connection.close();

        return emp;
    }

    public static Candidat findById(Connection connection, Integer idEmploye) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }

        Candidat emp = dao.select(connection, Candidat.class, new Candidat(idEmploye))[0];

        if (new_connex)
            connection.close();

        return emp;
    }

    @Override
    public String toString() {
        return String.format("""
                
                    Candidat : {
                        id : %s,
                        nom : %s,
                        prenom : %s,
                        dtn : %s,
                        genre : %s
                    }
                """, this.getId(), this.getNom(), this.prenom, this.getDtn(), this.genre.getNom());
    }
}
