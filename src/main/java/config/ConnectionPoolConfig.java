package config;

public class ConnectionPoolConfig {
    public static String url = "jdbc:sqlite:joboonja.db";
    public static int minPools = 5;
    public static int maxPools = 10;
    public static int maxOpenPreparedStatement = 100;
    public static String driver = "org.sqlite.JDBC";
}
