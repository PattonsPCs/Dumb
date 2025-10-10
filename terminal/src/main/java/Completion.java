import com.anthony.*;
import picocli.CommandLine;
import java.sql.SQLException;

@CommandLine.Command(name = "complete", description = "Complete a Task")
public class Completion implements Runnable {
    @CommandLine.Option(names = {"-db", "--database"}, required = true)
    String dbName;

    @CommandLine.Parameters(index = "0", paramLabel = "ID", description = "Task ID")
    int taskID;

    @Override
    public void run(){
        try{
            DatabaseManager dbManager = new DatabaseManager(dbName);
            Database db = dbManager.getDatabase();
            System.out.println("Connecting to database...");
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e){
                System.err.println("Error: " + e);
            }
            System.out.println("Connected!");
            System.out.println("Getting task...");
            AbstractTask neededTask = db.readTask(taskID);
            if(neededTask.getStatus() == Status.COMPLETE){
                System.out.println("Task already complete. Deleting...");
                db.deleteTask(taskID);
                System.exit(1);
            } else{
                neededTask.setStatus(Status.COMPLETE);
                db.saveTask(taskID, neededTask);
                System.out.println("Task completed!");
            }
        } catch (SQLException e){
            System.err.println("Error completing task: " + e);
        }
    }
}
