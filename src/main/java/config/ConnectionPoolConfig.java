package config;

public class ConnectionPoolConfig {
    public static String url = "jdbc:mysql://localhost:3306/joboonja?useSSL=false";
    public static String username = "root";
    public static String password = "13777731";
    public static int minPools = 5;
    public static int maxPools = 10;
    public static int maxOpenPreparedStatement = 100;
    public static String driver = "com.mysql.jdbc.Driver";
}