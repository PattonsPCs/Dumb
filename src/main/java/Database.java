import java.sql.Connection;
import java.util.Map;


public interface Database {

    Connection getConnection();
    void createTable();
    void createTask(int id, String contact);
    Map.Entry<Integer, Map.Entry<String, String>> readTask(int id);
    void saveTask(int id, String neededStatus);
    void deleteTask(int id);
    void deleteData();
    void dropTable();
}
