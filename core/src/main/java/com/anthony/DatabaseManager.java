package com.anthony;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private final Map<String, Database> databases = new HashMap<>();
    private final String dbType;
    public DatabaseManager(String dbType) throws SQLException{
        this.dbType = dbType;
        switch(dbType.toLowerCase()){
            case "sqlite" -> databases.put("sqlite", new SQLite(createSQLiteConnection()));
            case "h2" -> databases.put("h2", new H2(createH2Connection()));
        }
    }


    private Connection createH2Connection() throws SQLException{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:tcp://localhost:9092/h2-data/test");
        dataSource.setUsername("sa");
        dataSource.setPassword(null);
        return dataSource.getConnection();
    }

    private Connection createSQLiteConnection() throws SQLException{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlite:C:\\Users\\Patpat\\IdeaProjects\\SmthStupidIg\\sqlite.db");
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUsername(null);
        dataSource.setPassword(null);
        return dataSource.getConnection();
    }

    public Database getDatabase(){
        return databases.get(dbType.toLowerCase());
    }



}
