package models.data.connectionPool;

import config.ConnectionPoolConfig;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(ConnectionPoolConfig.url);
        ds.setDriverClassName(ConnectionPoolConfig.driver);
        ds.setMinIdle(ConnectionPoolConfig.minPools);
        ds.setMaxIdle(ConnectionPoolConfig.maxPools);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
