package models.data.connectionPool;

import config.ConnectionPoolConfig;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool ourInstance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return ourInstance;
    }
    private static  DataSource dataSource;
    static {
        dataSource = setupDataSource();
    }
    private static DataSource setupDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(ConnectionPoolConfig.driver);
        basicDataSource.setUrl(ConnectionPoolConfig.url);
        basicDataSource.setUsername(ConnectionPoolConfig.username);
        basicDataSource.setPassword(ConnectionPoolConfig.password);
        basicDataSource.setMinIdle(ConnectionPoolConfig.minPools);
        basicDataSource.setMaxIdle(ConnectionPoolConfig.maxPools);
        basicDataSource.setMaxOpenPreparedStatements(ConnectionPoolConfig.maxOpenPreparedStatement);
        return basicDataSource;
    }
    public static Connection getConnection() {
        while (true) {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                System.out.println("Another try to connect to database...");
                e.printStackTrace();
            }
        }
    }
}