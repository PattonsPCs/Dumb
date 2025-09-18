import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SQLite implements Database{
    private final Connection conn;

    public SQLite(Connection conn) throws SQLException {
        this.conn = conn;
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks(ID INT PRIMARY KEY, DATA BLOB)";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch(SQLException e){
            log.error("Failed to create table: {}", String.valueOf(e));
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
            log.error("Failed to create task: {}", String.valueOf(e));
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
            log.error("Failed to read raw data: {}", String.valueOf(e));
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
            log.error("Error reading task: {}", String.valueOf(e));
        }
        return null;
    }


    @Override
    public void saveTask(int id, Task task) {
        String sql = "UPDATE data = (?) WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.setBytes(2, task.serialize());
        } catch(Exception e){
            log.error("Error updating task: {}", String.valueOf(e));
        }
    }

    public List<Task> getAllTasks(){
        String sql = "SELECT * FROM tasks";
        List<Task> tasks = new ArrayList<>();
        try(ResultSet rs = conn.prepareStatement(sql).executeQuery()){
            while(rs.next()){
                byte[] bytes = rs.getBytes("data");
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                Task task = AbstractTask.deserialize(buffer);
                tasks.add(task);
            }
            return tasks;
        } catch(SQLException e){
            log.error("Error getting all tasks: {}", String.valueOf(e));
        }
        return null;
    }



    @Override
    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = (?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch(SQLException e){
            log.error("Error deleting task: {}", String.valueOf(e));
        }

    }

    @Override
    public void deleteData() {
        String sql = "DELETE FROM tasks";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e){
            log.error("Error deleting data: {}", String.valueOf(e));
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE tasks";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e){
            log.error("Error dropping table: {}", String.valueOf(e));
        }
    }
}


