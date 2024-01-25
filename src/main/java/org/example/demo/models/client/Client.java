package org.example.demo.models.client;

import org.example.demo.database.Connexion;
import org.example.demo.models.Activite;
import org.example.demo.models.promotionPoste.Embauche;
import org.example.demo.models.promotionPoste.Sexe;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;

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

    @ForeignKey(recursive = true)
    @Column("id_sexe")
    private Sexe sexe;

    public Client(Sexe sexe) {
        this.sexe = sexe;
    }

    public static void insertClient(Connection connection, Client client) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        try{
            dao.insertWithoutPrimaryKey(connection,client);
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
