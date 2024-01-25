package org.example.demo.models.benefice;

import org.example.demo.database.Connexion;
import org.example.demo.models.travail.Voyage;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

@Table("vue_voyage_complet_benefice_total")
public class BeneficeVoyage {
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
    @Column("id_voyage")
    private Voyage voyage;

    @Column("benefice_voyage")
    private Double beneficeVoyage;

    public BeneficeVoyage() {}

    public BeneficeVoyage(Voyage voyage, Double beneficeVoyage) {
        this.voyage = voyage;
        this.beneficeVoyage = beneficeVoyage;
    }

    public static List<BeneficeVoyage> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        BeneficeVoyage[] beneficeVoyage=dao.select(connection,BeneficeVoyage.class);
        if (new_connex)
            connection.close();
        return Arrays.asList(beneficeVoyage);
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Double getBeneficeVoyage() {
        return beneficeVoyage;
    }

    public void setBeneficeVoyage(Double beneficeVoyage) {
        this.beneficeVoyage = beneficeVoyage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
