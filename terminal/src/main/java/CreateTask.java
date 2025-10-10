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

    @CommandLine.Option(names = {"-dl", "--deadline"}, description = "Task Deadline (HH:mm - 24 Hour Clock)")
    String deadline;


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
            if(deadline != null){
                db.createTask(taskId, new ScheduleTask(taskName, Status.TODO, "Schedule Task", TimeFormatting.timeStringToMillis(deadline)));
            } else {
                db.createTask(taskId, new BasicTask(taskName, Status.TODO, "Basic Task"));
            }
        } catch(SQLException e){
            System.err.println("Error creating task: " + e);
        }
    }
}
