import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        SQLite db = new SQLite();
        BasicTask testTask = new BasicTask("Test", Status.TODO, "Basic Task");
        ScheduleTask nextTest = new ScheduleTask("ScheduleTest", Status.TODO, "A task with a deadline", 12000000);


        System.out.println(db.readTask(1));

    }

}
