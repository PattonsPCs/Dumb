import com.anthony.*;
import picocli.CommandLine;

import java.sql.SQLException;

@CommandLine.Command(name = "create", description = "Create a task")
public class CreateTask implements Runnable {
    @CommandLine.Option(names = {"-db", "--database"}, required = true)
    String dbName;

    @CommandLine.Parameters(index = "0", paramLabel = "ID", description = "Task ID")
    int taskId;
    @CommandLine.Parameters(index = "1", paramLabel = "NAME", description = "Task Name")
    String taskName;

    @Override
    public void run(){
        try {
            DatabaseManager dbManager = new DatabaseManager(dbName);
            Database db = dbManager.getDatabase();
            System.out.println("Connecting to database...");
            try{
                Thread.sleep(2000);
            } catch(InterruptedException e){
                System.err.println("Error: " + e);
            }
            System.out.println("Connected!");
            System.out.println("Creating task: " + taskName);
            db.createTable();
            db.createTask(taskId, new BasicTask(taskName, Status.TODO, "User created task."));
        } catch(SQLException e){
            System.err.println("Error creating task: " + e);
        }
    }
}
