import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private final HikariDataSource h2DataSource;
    private final HikariDataSource sqliteDataSource;

    public DatabaseManager() {
        h2DataSource = new HikariDataSource();
        h2DataSource.setJdbcUrl("jdbc:h2:tcp://localhost:9092/h2-data/test");
        h2DataSource.setUsername("sa");
        h2DataSource.setPassword("");

        sqliteDataSource = new HikariDataSource();
        sqliteDataSource.setJdbcUrl("jdbc:sqlite:SQLite");
    }

    public Connection getH2Connection() throws SQLException {
        return h2DataSource.getConnection();
    }

    public Connection getSqliteConnection() throws SQLException {
        return sqliteDataSource.getConnection();
    }


}
