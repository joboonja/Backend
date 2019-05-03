package models.data.user.mapper;

import config.DatabaseColumns;
import config.UserConfig;
import exceptions.UserNotFound;
import models.data.connectionPool.ConnectionPool;
import models.data.mapper.Mapper;
import models.data.skill.UserSkill;
import models.data.user.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMapper extends Mapper<User, String> implements IUserMapper{
    private static UserMapper ourInstance = new UserMapper();

    private ArrayList<User> users = new ArrayList<User>();

    public static UserMapper getInstance() {
        return ourInstance;
    }

    private UserMapper() {
        users = new ArrayList<User>();
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(String id) throws UserNotFound
    {
        for (User user : users) {
            if (user.getId().equals(id))
                return user;
        }
        throw new UserNotFound();
    }

    public void registerNewUser(User newUser)
    {
        for (User user : users) {
            if (user.getId().equals(newUser.getId())) {
                System.out.println(UserConfig.USERNAME_ALREADY_EXISTS_ERROR);
                return;
            }
        }
        users.add(newUser);
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    @Override
    public List<UserSkill> findUserSkills(String userId) throws SQLException{
        try (Connection con = ConnectionPool.getConnection();
             Statement stmt = con.createStatement();
        ) {
            ResultSet resultSet;
            try {
                String query = "SELECT " + DatabaseColumns.USER_SKILL + "FROM UserSkill WHERE usid = " + userId;
                resultSet = stmt.executeQuery(query);
                resultSet.next();
                //TODO: return statement
//                return convertResultSetToDomainModel(resultSet);
                return null;
            } catch (SQLException ex) {
                System.out.println();
                throw ex;
            }
        }
    }

    @Override
    public String getInsertStatement() {
        return "INSERT ";
    }

    @Override
    public void insert(User user) {

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + DatabaseColumns.USER_COLUMNS +
                " FROM JoboonjaUser" +
                " WHERE id = ?";
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<String>();
        String stmt1 = "CREATE TABLE IF NOT EXISTS JoboonjaUser(" +
                "userId CHAR(20), " +
                "firstName CHAR(20)," +
                "lastName CHAR(20)," +
                "profilePictureUrl TEXT," +
                "bio TEXT," +
                "jobTitle CHAR(50)," +
                "PRIMARY KEY(userId)" +
                ");";
        String stmt2 = "CREATE TABLE IF NOT EXISTS Endorsement(" +
                "userId CHAR(20)," +
                "name CHAR(20)," +
                "usid CHAR(20)," +
                "PRIMARY KEY(userId, name, usid)," +
                "FOREIGN KEY(userId) REFERENCES JoboonjaUser on DELETE CASCADE," +
                "FOREIGN KEY (name, usid) REFERENCES UserSkill on DELETE CASCADE" +
                ");";
        statements.add(stmt1);
        statements.add(stmt2);
        return statements;
    }

    public void addDefaultUser()
    {
        String id = "1";
        String firstName = "علی";
        String lastName = "شریف‌زاده";
        HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 10));
        skills.put("Javascript", new UserSkill("Javascript", 13));
        skills.put("C++", new UserSkill("C++", 12));
        skills.put("Java", new UserSkill("Java", 11));
        skills.put("MySQL", new UserSkill("MySQL", 11));
        skills.put("Linux", new UserSkill("Linux", 11));
        skills.put("C", new UserSkill("C", 11));
        skills.put("Node.js", new UserSkill("Node.js", 11));
        String jobTitle = "برنامه‌نویس وب";
        String bio = "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "2";
        firstName = "فرزاد";
        lastName = "حبیبی";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "دانشجو";
        bio = "بیو ندارم";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "3";
        firstName = "یاسمن";
        lastName = "جعفری";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "دانشجو";
        bio = "بیو؟";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "4";
        firstName = "ممد";
        lastName = "فراهانی";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "برنامه‌نویس";
        bio = "دنبال کار می‌گردم";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "5";
        firstName = "بهار";
        lastName = "باطنی";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "دانشجو";
        bio = "دنبال کار می‌گردم";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "6";
        firstName = "امیرحسین";
        lastName = "احمدی";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "دانشجو";
        bio = "دنبال کار می‌گردم";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

        id = "7";
        firstName = "غلام";
        lastName = "حسنی";
        skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 5));
        skills.put("Javascript", new UserSkill("Javascript", 4));
        skills.put("C++", new UserSkill("C++", 2));
        skills.put("Java", new UserSkill("Java", 3));
        jobTitle = "برنامه‌نویس وب";
        bio = "دنبال کار می‌گردم";
        users.add(new User(id, skills, firstName, lastName, jobTitle, bio));

    }

}
