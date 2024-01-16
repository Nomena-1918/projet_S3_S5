import org.example.demo.database.Connexion;
import org.example.demo.models.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class ClasseTest {
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
            VoyageActivite actbouq=new VoyageActivite(8L, 1L,"Excursion en montagne", 8L,"Bouquet EXTRA");
            VoyageActivite.insertActiviteBouquet(connection,actbouq);
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
            List<ActiviteBouquetPrix> listactbouq = ActiviteBouquetPrix.getVoyageBetweenPrix(connection,80000.0,500000.0);
            System.out.println("\n====================\n"+listactbouq+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
