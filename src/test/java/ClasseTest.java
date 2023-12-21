import org.example.demo.database.Connexion;
import org.example.demo.models.Activite;
import org.example.demo.models.ActiviteBouquet;
import org.example.demo.models.Bouquet;
import org.example.demo.models.Emp;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClasseTest {
    @Test
    void test() {
        System.out.println("Hello World!");
    }

    @Test
    void testReadAllEmp() {
        // Tous les emp avec pagination
        int nb_lignes = 4;
        int pagination_debut = 1;

        try(Connection connection = Connexion.getConnexionPostgreSql()) {
            List<Emp> listEmp = Emp.readAllEmp(connection, nb_lignes, pagination_debut);
            System.out.println("\n====================\n"+listEmp+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            ActiviteBouquet actbouq=new ActiviteBouquet(8L, 1L,"Excursion en montagne", 8L,"Bouquet EXTRA");
            actbouq.insertActiviteBouquet(connection,actbouq);
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
            List<ActiviteBouquet> listactbouq = ActiviteBouquet.findActiviteBouquet(connection,2L);
            System.out.println("\n====================\n"+listactbouq+"\n====================\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
