import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws SQLException {
        SQLite test = new SQLite();
        BasicTask testTask = new BasicTask("Test", Status.TODO, "Basic Task");

        System.out.println("Raw Data: " + Arrays.toString(test.readRawData(1)));
        System.out.println("Task Data: \n" + test.readTask(1).toString());

    }
}
