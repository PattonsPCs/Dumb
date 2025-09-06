import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws SQLException {
        SQLite sqlite = new SQLite("jdbc:sqlite:sqliteDb");
        H2 h2 = new H2("jdbc:h2:tcp://localhost:9092/h2-data/test", "sa", null);


        sqlite.deleteData();
        sqlite.dropTable();

        h2.deleteData();
        h2.dropTable();


    }
}
