import org.voyage.demo.connexion.Connexion;
import org.voyage.demo.models.composition_voyage.VoyageActivite;
import org.voyage.demo.models.gestion_personnel.*;
import org.voyage.demo.models.gestion_reservation.BeneficeVoyage;
import org.voyage.demo.models.gestion_reservation.Client;
import org.voyage.demo.models.composition_voyage.Activite;
import org.voyage.demo.models.composition_voyage.Bouquet;
import org.voyage.demo.models.composition_voyage.Voyage;
import org.junit.jupiter.api.Test;
import veda.godao.DAO;
import veda.godao.utils.Constantes;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ClasseTest {
    private static final DAO dao;
    static {
        dao=new DAO(
                "voyage_db",
                "localhost",
                "5432",
                "nomena",
                "root",
                false,
                Constantes.PSQL_ID);
    }

    @Test
    void testVoyage() throws Exception {
        Voyage[] voyageList = dao.select(null, Voyage.class);
        System.out.println(Arrays.toString(voyageList));
    }

    @Test
    void testClient() throws Exception {
        Client.insertClient(null, new Client("Clienttest-"+LocalDateTime.now(), new Genre(1)));
        System.out.println("ok");
    }

    @Test
    void testSelectClient() throws Exception {
        var list = Client.readAll(Connexion.getConnexionPostgreSql());
        System.out.println(list);
    }

    @Test
    void testSelectGrade() throws Exception {
        var list = GradeFonction.readAll(Connexion.getConnexionPostgreSql());
        System.out.println(list);
    }

    @Test
    void testSelectFonction() throws Exception {
        var list = dao.select(Connexion.getConnexionPostgreSql(), Fonction.class);
        System.out.println(Arrays.toString(list));
    }

    @Test
    void testSelectEmp() throws Exception {
        var list = Employe.readAll(Connexion.getConnexionPostgreSql());
        System.out.println(list);
    }


    @Test
    void testSelectSituationPro() throws Exception {
        var list = SituationProPersonne.readAll(Connexion.getConnexionPostgreSql());
        System.out.println(list);
    }

    @Test
    void testBenefice() throws Exception {
        BeneficeVoyage[] beneficeVoyage=dao.select(null, BeneficeVoyage.class);
        System.out.println(Arrays.toString(beneficeVoyage));
    }

    @Test
    void testVoyageSelect() throws Exception {
        Voyage[] voyage=dao.select(null,Voyage.class);
        System.out.println(Arrays.toString(voyage));
    }

    @Test
    void test() {
        System.out.println("Hello World!");
    }

    @Test
    void testInsertActivite() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            Activite activite=new Activite();
            activite.setNom("Voyage a Mananjary - "+LocalDateTime.now());
            Activite.insertActivite(connection,activite);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testInsertBouquet() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            Bouquet bouquet=new Bouquet();
            bouquet.setNom("Bouquet EXTRA - "+LocalDateTime.now());
            Bouquet.insertBouquet(connection,bouquet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReadAllActivite() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            List<Activite> listact = Activite.readAll(connection);
            System.out.println("\n====================\n"+listact+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReadAllBouquet() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            List<Bouquet> listbouq = Bouquet.readAll(connection);
            System.out.println("\n====================\n"+listbouq+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testfindActiviteBouquet() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            List<VoyageActivite> listactbouq = VoyageActivite.findActiviteBouquet(connection,2L);
            System.out.println("\n====================\n"+listactbouq+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFonctions() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            List<Fonction> listactbouq = Fonction.readAll(connection);
            System.out.println("\n====================\n"+listactbouq+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
