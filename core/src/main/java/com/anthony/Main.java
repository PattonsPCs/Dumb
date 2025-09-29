package com.anthony;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args){
        try{
            DatabaseManager dbManager = new DatabaseManager("h2");
            Database db = dbManager.getDatabase();

            db.deleteData();
        } catch(SQLException e){
            System.err.println("Error deleting data: " + e);
        }
    }
}
