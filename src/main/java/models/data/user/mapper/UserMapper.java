package models.data.user.mapper;

import config.DatabaseColumns;
import config.UserConfig;
import exceptions.UserNotFound;
import models.data.mapper.Mapper;
import models.data.skill.UserSkill;
import models.data.skill.mapper.UserSkillMapper.UserSkillMapper;
import models.data.user.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserMapper extends Mapper<User, String> implements IUserMapper{
    private static UserMapper ourInstance = new UserMapper();

    private ArrayList<User> users;

    public static UserMapper getInstance() {
        return ourInstance;
    }

    private UserMapper() {
        users = new ArrayList<>();
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

//    @Override
//    public List<UserSkill> findUserSkills(String userId) throws SQLException{
//        try (Connection con = ConnectionPool.getConnection();
//             Statement stmt = con.createStatement()
//        ) {
//            ResultSet resultSet;
//            try {
//                String query = "SELECT " + DatabaseColumns.USER_SKILL + "FROM UserSkill WHERE usid = " + userId;
//                resultSet = stmt.executeQuery(query);
//                resultSet.next();
//                //TODO: return statement
////                return convertResultSetToDomainModel(resultSet);
//                return null;
//            } catch (SQLException ex) {
//                System.out.println();
//                throw ex;
//            }
//        }
//    }

    @Override
    public String getInsertStatement() {
        return "INSERT OR IGNORE INTO JoboonjaUser (" + DatabaseColumns.USER_COLUMNS + ")" +
                "VALUES(?, ?, ?, ?, ?, ?) ";
    }

    @Override
    public void fillInsertStatement(PreparedStatement stmt, User object) throws SQLException {
        stmt.setString(1, object.getId());
        stmt.setString(2, object.getFirstName());
        stmt.setString(3, object.getLastName());
        stmt.setString(4, object.getProfilePictureURL());
        stmt.setString(5, object.getBio());
        stmt.setString(6, object.getJobTitle());
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + DatabaseColumns.USER_COLUMNS +
                " FROM JoboonjaUser" +
                " WHERE id = ?";
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
//        " userId, firstName, lastName, profilePictureUrl, bio, jobTitle"
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

    private void storeUserData(String id, HashMap<String, UserSkill> skills, String firstName, String lastName, String jobTitle, String bio) throws SQLException {
        User newUser;
        UserMapper userMapper = UserMapper.getInstance();
        UserSkillMapper userSkillMapper = UserSkillMapper.getInstance();

        newUser = new User(id, skills, firstName, lastName, jobTitle, bio);
        users.add(newUser);
        userMapper.insert(newUser);

        for(UserSkill skill: skills.values()){
            userSkillMapper.insert(skill);
        }
    }

    public void addDefaultUser() throws SQLException {

        String id = "1";
        String firstName = "علی";
        String lastName = "شریف‌زاده";
        HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 10));
        skills.put("Javascript", new UserSkill("Javascript", 13));
        skills.put("C++", new UserSkill("C++", 12));
        skills.put("Java", new UserSkill("Java", 11));
        skills.put("SQL", new UserSkill("SQL", 11));
        skills.put("Linux", new UserSkill("Linux", 11));
        skills.put("C", new UserSkill("C", 11));
        skills.put("Node.js", new UserSkill("Node.js", 11));
        String jobTitle = "برنامه‌نویس وب";
        String bio = "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio);

        id = "2";
        firstName = "فرزاد";
        lastName = "حبیبی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "2"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "2"));
        skills.put("C++", new UserSkill("C++", 2, "2"));
        skills.put("Java", new UserSkill("Java", 3, "2"));
        jobTitle = "دانشجو";
        bio = "بیو ندارم";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio);

        id = "3";
        firstName = "یاسمن";
        lastName = "جعفری";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "3"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "3"));
        skills.put("C++", new UserSkill("C++", 2, "3"));
        skills.put("Java", new UserSkill("Java", 3, "3"));
        jobTitle = "دانشجو";
        bio = "بیو؟";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio);

        id = "4";
        firstName = "ممد";
        lastName = "فراهانی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "4"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "4"));
        skills.put("C++", new UserSkill("C++", 2, "4"));
        skills.put("Java", new UserSkill("Java", 3, "4"));
        jobTitle = "برنامه‌نویس";
        bio = "دنبال کار می‌گردم";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio);


        id = "5";
        firstName = "بهار";
        lastName = "باطنی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "5"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "5"));
        skills.put("C++", new UserSkill("C++", 2, "5"));
        skills.put("Java", new UserSkill("Java", 3, "5"));
        jobTitle = "دانشجو";
        bio = "دنبال کار می‌گردم";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio);

        id = "6";
        firstName = "امیرحسین";
        lastName = "احمدی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "6"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "6"));
        skills.put("C++", new UserSkill("C++", 2, "6"));
        skills.put("Java", new UserSkill("Java", 3, "6"));
        jobTitle = "دانشجو";
        bio = "دنبال کار می‌گردم";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio);

        id = "7";
        firstName = "غلام";
        lastName = "حسنی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "7"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "7"));
        skills.put("C++", new UserSkill("C++", 2, "7"));
        skills.put("Java", new UserSkill("Java", 3, "7"));
        jobTitle = "برنامه‌نویس وب";
        bio = "دنبال کار می‌گردم";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio);

    }

}
