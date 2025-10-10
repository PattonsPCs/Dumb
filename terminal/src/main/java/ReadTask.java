import picocli.CommandLine;
import com.anthony.*;

import java.sql.SQLException;

@CommandLine.Command(name = "read", description = "Read a task")
public class ReadTask implements Runnable{
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
            } catch(InterruptedException e){
                System.err.println("Error: " + e);
            }
            System.out.println("Connected!");
            System.out.println("Reading task: " + taskID);
            System.out.println(db.readTask(taskID));
            if(db.readTask(taskID) instanceof ScheduleTask schedTask){
                if(schedTask.isLate()){
                    System.out.println("Task is late. Deleting...");
                    db.deleteTask(taskID);
                } else if (schedTask.getStatus() == Status.TODO){
                    schedTask.setStatus(Status.IN_PROGRESS);
                    db.saveTask(taskID, schedTask);
                }
            }
        } catch(SQLException e){
            System.err.println("Error while reading task: " + e);
        }
    }
}
