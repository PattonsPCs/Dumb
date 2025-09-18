import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        String dbName = null, user = null, password = null, action = null, taskName = null;

        for(int i = 0; i < args.length; i++){
            switch(args[i]) {
                case "--db":
                    dbName = args[++i];
                    break;
                case "--user":
                    user = args[++i];
                    break;
                case "--pass":
                    password = args[++i];
                    break;
                case "--create-task":
                    action = "create-task";
                    taskName = args[++i];
                    break;
                case "--read-task":
                    action = "read-task";
                    taskName = args[++i];
                    break;
                case "--update-task":
                    action = "update-task";
                    taskName = args[++i];
                case "--delete-task":
                    action = "delete-task";
                    taskName = args[++i];
            }
        }

        if(dbName == null || (!dbName.equals("h2") && !dbName.equals("sqlite"))){
            System.out.println("Usage: --db <h2|sqlite> --user <username> --pass <password> [--create-task <taskname> | --read-task <taskname> | --update-task <taskname> | --delete-task <taskname>]");
            return;
        }


        System.out.println("Connecting to database: " + dbName + "...");
        DatabaseManager dbManager = new DatabaseManager();
        try{
            dbManager.getConnection(dbName, user, password);
            System.out.println("Connected to database: " + dbName);
        } catch (Exception e){
            System.err.println("Failed to connect to database: " + e);
        }


    }

}
