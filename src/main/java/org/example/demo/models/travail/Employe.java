package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.Bouquet;
import org.example.demo.models.CategorieLieu;
import org.example.demo.models.TypeDuree;
import org.example.demo.models.promotionPoste.Sexe;
import org.example.demo.models.promotionPoste.SituationProPersonne;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;
import veda.godao.utils.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Table("employe")
public class Employe {
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
    private Sexe sexe;

    public Employe() {

    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Employe(String nom, String prenom, LocalDate dtn, Sexe sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.dtn = dtn;
        this.sexe = sexe;
    }

    public Employe(Integer id) {
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

    public static void insertEmploye(Connection connection, Employe employe) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        try{
            dao.insertWithoutPrimaryKey(connection,employe);
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
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }

        SituationProPersonne[] employes=dao.select(connection,SituationProPersonne.class);

        List<Employe> emp = new ArrayList<>();
        for (SituationProPersonne s : employes) {
            emp.add(s.getEmploye());
        }

        if (new_connex)
            connection.close();

        return emp;
    }

    public static Employe findById(Connection connection, Integer idEmploye) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }

        Employe emp = dao.select(connection, Employe.class, new Employe(idEmploye))[0];

        if (new_connex)
            connection.close();

        return emp;
    }

    @Override
    public String toString() {
        return String.format("""
                
                    Emp : {
                        id : %s,
                        nom : %s,
                        prenom : %s,
                        dtn : %s,
                        sexe : %s
                    }
                """, this.getId(), this.getNom(), this.prenom, this.getDtn(), this.sexe.getNom());
    }
}
