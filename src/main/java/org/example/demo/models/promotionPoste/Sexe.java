package org.example.demo.models.promotionPoste;

import org.example.demo.database.Connexion;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table("sexe")
public class Sexe {
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

    public Sexe(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Sexe() {
    }

    public Sexe(String nom) {
        this.nom = nom;
    }

    public Sexe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static List<Sexe> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        Sexe[] sexes=dao.select(connection,Sexe.class);

        if (new_connex)
            connection.close();
        return Arrays.asList(sexes);
    }

    public static void insertSexe(Connection connection, Sexe sexe) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        dao.insertWithoutPrimaryKey(connection, sexe);
        if (new_connex) {
            connection.commit();
            connection.close();
        }
    }



}
