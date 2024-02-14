package org.voyage.demo.models.gestion_personnel;

import org.voyage.demo.connexion.Connexion;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

@Table("genre")
public class Genre {
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

    public Genre(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Genre() {
    }

    public Genre(String nom) {
        this.nom = nom;
    }

    public Genre(Integer id) {
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

    public static List<Genre> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        Genre[] sexes=dao.select(connection, Genre.class);

        if (new_connex)
            connection.close();
        return Arrays.asList(sexes);
    }

    public static void insertSexe(Connection connection, Genre genre) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        dao.insertWithoutPrimaryKey(connection, genre);
        if (new_connex) {
            connection.commit();
            connection.close();
        }
    }



}
