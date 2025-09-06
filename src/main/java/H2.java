import java.sql.*;
import java.util.Map;

public class H2 implements Database{

    private final Connection conn;

    public H2(String url, String username, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, username, password); // I haven't done this in so long I was fuckin confused
        // TODO learn HikariCP

    }

    @Override
    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS tasks(ID INT PRIMARY KEY, NAME VARCHAR(255), STATUS VARCHAR(255))";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch(SQLException e){
            System.err.println("Failed to create table: " + e);
        }
    }

    // Da fuq is dis

    @Override // TODO Create a new *Basic Task* object which implements *Task*
              // TODO which you then enter into the database as well as returning the object
    public void createTask(int id, String contact){
        String sql = "INSERT INTO tasks VALUES(?, ?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.setString(2, contact);
            pstmt.setString(3, "TODO");
            pstmt.executeUpdate();
        } catch(SQLException e){
            System.err.println("Failed to create task: " + e);
        }
    }

    // HOLY FUCK THE AUTISM IS STRONG WITH THIS ONE
    @Override // TODO MAKE THE FRIGGIN TASK INTERFACE AND BASICTASK CLASS
    public Map.Entry<Integer, Map.Entry<String, String>> readTask(int id) {
        String sql = "SELECT name, status FROM tasks WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    String taskName = rs.getString("name");
                    String taskStatus = rs.getString("status");
                    return Map.entry(id, Map.entry(taskName, taskStatus));
                }
            }
        } catch(SQLException e){
            System.err.println("Error reading task: " + e);
        }
        return null;
    }

    @Override
    public void saveTask(int id, String neededStatus) {
        String sql = "UPDATE status = (?) WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.setString(3, neededStatus);
            pstmt.executeUpdate();
        } catch(SQLException e){
            System.err.println("Error updating task: " + e);
        }

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
