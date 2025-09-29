import com.anthony.Database;
import com.anthony.DatabaseManager;
import picocli.CommandLine;

import java.sql.SQLException;


@CommandLine.Command(name = "delete", description = "Delete a task")
public class DeleteTask implements Runnable {
    @CommandLine.Option(names = {"-db", "--database"}, required = true)
    String dbName;

    @CommandLine.Parameters(index = "0", paramLabel = "ID", description = "Task ID")
    int taskID;

    @Override
    public void run() {
        try{
            DatabaseManager dbManager = new DatabaseManager(dbName);
            Database db = dbManager.getDatabase();
            System.out.println("Connecting to database...");
            try{
                Thread.sleep(2000);
            } catch(InterruptedException e){
                System.err.println("Error: " + e);
            }
            System.out.println("Connected!");
            System.out.println("Deleting task: " + taskID);
            db.deleteTask(taskID);
            System.out.println("Success!");
        } catch(SQLException e){
            System.err.println("Error while reading task: " + e);
        }
    }
}
