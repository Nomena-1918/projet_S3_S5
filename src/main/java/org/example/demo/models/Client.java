package org.example.demo.models;
import org.example.demo.database.Connexion;
import org.example.demo.models.promotionPoste.Sexe;
import veda.godao.DAO;
import veda.godao.annotations.*;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

@Table("client")
public class Client {
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
    @ForeignKey(recursive=true)
    @Column("id_sexe")
    private Sexe sexe;

    public Client() {
    }

    public Client(Integer id) {
        this.id = id;
    }

    public static void insertClient(Connection connection, Client client) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        dao.insertWithoutPrimaryKey(connection,client);
        if (new_connex)
            connection.close();
    }
    public static List<Client> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        Client[] clients=dao.select(connection,Client.class);

        if (new_connex)
            connection.close();
        return Arrays.asList(clients);
    }

    public Client(String nom, Sexe sexe) {
        this.nom = nom;
        this.sexe = sexe;
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

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }
}
