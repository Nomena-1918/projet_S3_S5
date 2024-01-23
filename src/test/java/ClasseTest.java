import org.example.demo.database.Connexion;
import org.example.demo.models.*;
import org.example.demo.models.travail.Fonction;
import org.junit.jupiter.api.Test;
import veda.godao.DAO;
import veda.godao.utils.Constantes;

import java.sql.Connection;
import java.util.List;

public class ClasseTest {
    private static final DAO dao;
    static {
        dao=new DAO(
                "test_veda",
                "localhost",
                "5432",
                "nomena",
                "root",
                false,
                Constantes.PSQL_ID);
    }
    @Test
    void test() {
        System.out.println("Hello World!");
    }
    @Test
    void testInsertActivite() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            Activite activite=new Activite();
            activite.setNom("Voyage a Mananjary");
            Activite.insertActivite(connection,activite);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testInsertBouquet() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            Bouquet bouquet=new Bouquet();
            bouquet.setNom("Bouquet EXTRA");
            Bouquet.insertBouquet(connection,bouquet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testInsertActiviteBouquet() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {

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
    void getVoyageBetweenPrix() {
        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            List<Fonction> listactbouq = Fonction.readAll(connection);
            System.out.println("\n====================\n"+listactbouq+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
