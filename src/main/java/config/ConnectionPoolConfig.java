package config;

public class ConnectionPoolConfig {
    public static String url = "jdbc:mysql://185.166.107.169:30718/joboonja?useSSL=false";
    public static String username = "root";
    public static String password = "farzad_yasaman";
    public static int minPools = 5;
    public static int maxPools = 10;
    public static int maxOpenPreparedStatement = 100;
    public static String driver = "com.mysql.cj.jdbc.Driver";
}