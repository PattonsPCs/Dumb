import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private final HikariDataSource dataSource;


    public DatabaseManager() {
        dataSource = new HikariDataSource();
    }


    public Connection getConnection(String dbType, String user, String pass) throws SQLException{
        if(dbType.equals("h2")){
            dataSource.setJdbcUrl("jdbc:h2:tcp://localhost:9092/h2-data/test");
            dataSource.setUsername(user);
            dataSource.setPassword(pass);
            return dataSource.getConnection();
        } else if(dbType.equals("sqlite")){
            dataSource.setJdbcUrl("jdbc:sqlite:SQLite");
            dataSource.setUsername(null);
            dataSource.setPassword(null);
            return dataSource.getConnection();
        }
        throw new IllegalArgumentException("Invalid Database Type");
    }

}
