import org.example.demo.database.Connexion;
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

}
