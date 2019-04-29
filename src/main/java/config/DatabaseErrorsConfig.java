package config;

public class DatabaseErrorsConfig {
    public static String createTable = "Error in Mapper. Create table query.";
    public static String canNotCreateTable(String className){return  "Can not create Table" + className ;}
}
