package com.anthony;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2 implements Database{



    private final Connection conn;

    public H2(Connection conn){
        this.conn = conn;
    }


    @Override
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS tasks(ID INT PRIMARY KEY, DATA BLOB)";
        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch(SQLException e){
            System.err.println("Failed to create table: " + e);
        }
    }


    @Override
    public void createTask(int id, Task task){
       try{
           byte[] data = task.serialize();
           String sql = "INSERT INTO tasks VALUES(?, ?)";
           try(PreparedStatement pStmt = conn.prepareStatement(sql)){
               pStmt.setInt(1, id);
               pStmt.setBytes(2, data);
               pStmt.executeUpdate();
           }
       } catch (Exception e) {
           System.err.println("Failed to create task: " + e);
       }
    }

    @Override
    public AbstractTask readTask(int id) {
        String sql = "SELECT data FROM tasks WHERE id = (?)";
        try(PreparedStatement pStmt = conn.prepareStatement(sql)){
            pStmt.setInt(1, id);
            try(ResultSet rs = pStmt.executeQuery()){
                if(rs.next()){
                    byte[] clazzBytes = rs.getBytes("data");
                    ByteBuffer buffer = ByteBuffer.wrap(clazzBytes);
                    return AbstractTask.deserialize(buffer);
                }
            }
        } catch(SQLException e){
            System.err.println("Error reading task: " + e);
        }
        return null;
    }



    @Override
    public void saveTask(int id, Task task) {
        String sql = "UPDATE data = (?) WHERE id = (?)";
        try(PreparedStatement pStmt = conn.prepareStatement(sql)){
            pStmt.setInt(1, id);
            pStmt.setBytes(2, task.serialize());
        } catch(Exception e){
            System.err.println("Error updating task: " + e);
        }

    }

    @Override
    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = (?)";
        try(PreparedStatement pStmt = conn.prepareStatement(sql)){
            pStmt.setInt(1, id);
            pStmt.executeUpdate();
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

    @Override
    public List<Integer> getAllTaskIds(){
        String sql = "SELECT id FROM tasks";
        List<Integer> ids = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    ids.add(rs.getInt("id"));
                }
            }
        } catch(SQLException e){
            System.err.println("Error getting task typeId: " + e);
        }
        return ids;
    }

}
