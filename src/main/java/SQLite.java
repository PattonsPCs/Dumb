import java.sql.*;
import java.util.Map;
/*
public class SQLite implements Database{
    private final Connection conn;

    public SQLite(String url) throws SQLException {
        this.conn = DriverManager.getConnection(url);
    }


    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks(ID INT PRIMARY KEY, NAME VARCHAR(255), STATUS VARCHAR(255))";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch(SQLException e){
            System.err.println("Failed to create table: " + e);
        }
    }

    @Override
    public void createTask(int id, Task task) {
        String sql = "INSERT INTO tasks VALUES(?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.setString(2, clazz);
            pstmt.setString(3, "TODO");
            pstmt.executeUpdate();
        } catch(SQLException e){
            System.err.println("Failed to create task: " + e);
        }
    }

    @Override
    public AbstractTask readTask(int id) {
        String sql = "SELECT id, name, status FROM tasks WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    int taskId = rs.getInt("id");
                    String taskName = rs.getString("name");
                    String taskStatus = rs.getString("status");
                    return Map.entry(taskId, taskName);
                }
            }
        } catch(SQLException e){
            System.err.println("Error reading task: " + e);
        }
        return null;
    }

    @Override
    public void saveTask(int id, String neededStatus) {

    }

    @Override
    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch(SQLException e){
            System.err.println("Error deleting task: " + e);
        }

    }

    @Override
    public void deleteData() {
        String sql = "DELETE FROM tasks";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e){
            System.err.println("Error deleting data: " + e);
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE tasks";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e){
            System.err.println("Error dropping table: " + e);
        }
    }
}


 */