package org.voyage.demo.models.gestion_personnel;

import org.voyage.demo.connexion.Connexion;
import veda.godao.DAO;
import veda.godao.annotations.*;

import java.sql.Connection;
import java.time.LocalDate;

@Table("embauche_employe")
public class Embauche {
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
    @ForeignKey(recursive = true)
    @Column("id_emp")
    private Employe employe;
    @ForeignKey(recursive = true)
    @Column("id_fonction")
    private Fonction fonction;
    @Column("date_embauche")
    private LocalDate date;

    public Embauche(Employe employe, Fonction fonction, LocalDate date) {
        this.employe = employe;
        this.fonction = fonction;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public static void insertEmbauche(Connection connection, Embauche embauche) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        try{
            dao.insertWithoutPrimaryKey(connection,embauche);
            connection.commit();
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }

        if (new_connex)
            connection.close();
    }
}
