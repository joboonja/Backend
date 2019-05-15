package models.data.user.mapper;

import com.google.common.hash.Hashing;
import config.DatabaseColumns;
import config.ProjectConfig;
import config.UserConfig;
import exceptions.UserAlreadyExists;
import exceptions.UserNotFound;
import models.data.connectionPool.ConnectionPool;
import models.data.mapper.Mapper;
import models.data.project.Project;
import models.data.skill.UserSkill;
import models.data.skill.mapper.UserSkillMapper.UserSkillMapper;
import models.data.user.User;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.CheckedOutputStream;

import static models.data.skill.UserSkill.convertToNameAndSkill;

public class UserMapper extends Mapper<User, String> implements IUserMapper{
    private static UserMapper ourInstance = new UserMapper();

    public static UserMapper getInstance() {
        return ourInstance;
    }

    private UserMapper() {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(String id) throws UserNotFound, SQLException {
        User user;
        user = find(id);
        if(user != null)
            return user;
        else
            throw new UserNotFound();
    }

    public void registerNewUser(User newUser) throws SQLException, UserAlreadyExists {
        if(find(newUser.getId()) != null) {
            throw new UserAlreadyExists();
        }
        insert(newUser);
    }

    @Override
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> result = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             Statement stmt = con.createStatement();
        ) {
            ResultSet resultSet;
            resultSet = stmt.executeQuery(getAllUsersStatement());
            while(resultSet.next())
            {
                result.add(convertResultSetToDomainModel(resultSet));
            }
            resultSet.close();
            stmt.close();
            con.close();
            return result;
        }
    }

    private String getAllUsersStatement() {
        return "SELECT * " +
            "FROM JoboonjaUser U";
    }

    private String getSearchByNameStatement() {
        return "SELECT DISTINCT " + DatabaseColumns.USER_COLUMNS +
                " FROM JoboonjaUser U" +
                " WHERE U.firstName LIKE ? OR " +
                " U.lastName LIKE ? ";
    }

    @Override
    public ArrayList<User> searchByName(String query) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     getSearchByNameStatement())
        ) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ArrayList<User> result = new ArrayList<>();
            ResultSet resultSet;
            resultSet = stmt.executeQuery();
            while(resultSet.next())
                result.add(convertResultSetToDomainModel(resultSet));
            resultSet.close();
            stmt.close();
            con.close();
            return result;
        }
    }

    @Override
    public String getInsertStatement() {
        return "INSERT OR IGNORE INTO JoboonjaUser (" + DatabaseColumns.USER_COLUMNS + ")" +
                "VALUES(?, ?, ?, ?, ?, ?, ?) ";
    }

    @Override
    public void fillInsertStatement(PreparedStatement stmt, User object) throws SQLException {
        stmt.setString(1, object.getId());
        stmt.setString(2, object.getFirstName());
        stmt.setString(3, object.getLastName());
        stmt.setString(4, object.getProfilePictureURL());
        stmt.setString(5, object.getBio());
        stmt.setString(6, object.getJobTitle());
        stmt.setString(7, object.getPassword());
    }

    @Override
    protected String getFindStatement() {
        return "SELECT *" +
                " FROM JoboonjaUser" +
                " WHERE userId = ?";
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        UserSkillMapper userSkillMapper = UserSkillMapper.getInstance();
        ArrayList <UserSkill> skills = userSkillMapper.getUserSkills(rs.getString(1));
        HashMap <String, UserSkill> hasSkills = convertToNameAndSkill(skills);
        User newUser = new User (
                rs.getString(1),
                hasSkills,
                rs.getString(2),
                rs.getString(3),
                rs.getString(6),
                rs.getString(5),
                rs.getString(4),
                rs.getString(7)
        );
        return newUser;
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
                "password CHAR(50)," +
                "PRIMARY KEY(userId)" +
                ");";

        statements.add(stmt1);
        return statements;
    }

    private void storeUserData(String id,
                               HashMap<String, UserSkill> skills,
                               String firstName,
                               String lastName,
                               String jobTitle,
                               String bio,
                               String profilePictureUrl,
                               String password) throws SQLException {
        User newUser;
        UserMapper userMapper = UserMapper.getInstance();
        UserSkillMapper userSkillMapper = UserSkillMapper.getInstance();

        newUser = new User(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);
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
        String password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        String profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

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
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

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
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

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
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

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
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

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
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

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
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

        id = "8";
        firstName = "صدف";
        lastName = "صادقیان";
        skills = new HashMap<>();
        skills.put("C", new UserSkill("C", 5, "8"));
        skills.put("Javascript", new UserSkill("Javascript", 2, "8"));
        skills.put("C++", new UserSkill("C++", 4, "8"));
        skills.put("Java", new UserSkill("Python", 6, "8"));
        skills.put("HTML", new UserSkill("HTML", 1, "8"));
        jobTitle = "دانشجو";
        bio = "در جستجوی دیتا";
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = UserConfig.MAIN_USER_PROFILE_PIC_URL;
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

    }

}
