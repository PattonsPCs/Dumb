import java.nio.ByteBuffer;
import java.sql.*;
import java.util.Map;

public class SQLite implements Database{
    private final Connection conn;

    public SQLite() throws SQLException {
        DatabaseManager manager = new DatabaseManager();
        this.conn = manager.getSqliteConnection();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks(ID INT PRIMARY KEY, DATA BLOB)";
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
            pstmt.setBytes(2, task.serialize());
            pstmt.executeUpdate();
        } catch(SQLException e){
            System.err.println("Failed to create task: " + e);
        }
    }

    public byte[] readRawData(int id){
        String sql = "SELECT data FROM tasks WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getBytes("data");
                }
            }
        } catch (SQLException e){
            System.err.println("Failed to read raw data: " + e);
        }
        return null;
    }

    @Override
    public AbstractTask readTask(int id) {
        String sql = "SELECT data FROM tasks WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    byte[] bytes = rs.getBytes("data");
                    ByteBuffer buffer = ByteBuffer.wrap(bytes);
                    return AbstractTask.deserialize(buffer);
                }
            }
        } catch(SQLException e){
            System.err.println("Error reading task: " + e);
        }
        return null;
    }


    @Override
    public void saveTask(int id, String neededStatus) {
        String sql = "UPDATE data = (?) WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            AbstractTask task = readTask(id);
            task.setStatus(Status.valueOf(neededStatus));
            pstmt.setBytes(2, task.serialize());
        } catch(Exception e){
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


