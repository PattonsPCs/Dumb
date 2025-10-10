import com.anthony.*;
import picocli.CommandLine;

import java.sql.SQLException;


@CommandLine.Command(name = "update", description = "Update a task" )
public class UpdateTask implements Runnable{
    @CommandLine.Option(names = {"-db", "--database"}, required = true)
    String dbName;

    @CommandLine.Parameters(index = "0", paramLabel = "ID", description = "Task ID")
    int taskId;
    @CommandLine.Parameters(index = "1", paramLabel = "DEADLINE", description = "New Task Deadline (HH:mm - 24 Hours)")
    String taskDeadline;


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
            System.out.println("Reading task...");
            AbstractTask updTask = db.readTask(taskId);
            if(updTask.getStatus() == Status.COMPLETE){
                System.out.println("Cannot update a task that has been completed.");
                System.exit(1);
            }
            if(updTask instanceof ScheduleTask schedTask){
                schedTask.setDeadline(TimeFormatting.timeStringToMillis(taskDeadline));
                db.saveTask(taskId, schedTask);
                System.out.println("Deadline updated.");
            } else{
                System.out.println("This task does not have a deadline. Closing connection...");
                System.exit(1);
            }
        } catch (SQLException e){
            System.err.println("Failed to update task:" + e);
        }
    }
}
