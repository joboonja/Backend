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
import org.springframework.web.bind.annotation.CrossOrigin;

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

    @Override
    public boolean validateUser(String id, String password) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     getValidateUserStatement())
        ) {
            fillValidateUserStatement(stmt, id, password);
            ResultSet resultSet;
            resultSet = stmt.executeQuery();
            boolean valid = false;
            if(resultSet.next())
                valid = true;
            resultSet.close();
            stmt.close();
            con.close();
            return valid;
        }
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
    public String getValidateUserStatement() {
        return "SELECT * " +
                "FROM JoboonjaUser U " +
                "WHERE U.userId = ? " +
                "AND U.password = ? ";
    }

    @Override
    public void fillValidateUserStatement(PreparedStatement st, String id, String pass) throws SQLException {
        st.setString(1, id);
        st.setString(2, pass);
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
        return "INSERT IGNORE INTO JoboonjaUser (" + DatabaseColumns.USER_COLUMNS + ")" +
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
        return new User (
                rs.getString(1),
                hasSkills,
                rs.getString(2),
                rs.getString(3),
                rs.getString(6),
                rs.getString(5),
                rs.getString(4),
                rs.getString(7)
        );
    }

    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<String>();
        String stmt1 = "CREATE TABLE IF NOT EXISTS JoboonjaUser(" +
                "userId CHAR(40), " +
                "firstName CHAR(40)," +
                "lastName CHAR(40)," +
                "profilePictureUrl TEXT," +
                "bio TEXT," +
                "jobTitle CHAR(50)," +
                "password TEXT," +
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

        String id = "farzad";
        String firstName = "فرزاد";
        String lastName = "حبیبی";
        HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
        skills.put("HTML", new UserSkill("HTML", 10, "farzad"));
        skills.put("Javascript", new UserSkill("Javascript", 13, "farzad"));
        skills.put("C++", new UserSkill("C++", 12, "farzad"));
        skills.put("Java", new UserSkill("Java", 11, "farzad"));
        skills.put("SQL", new UserSkill("SQL", 11, "farzad"));
        skills.put("Linux", new UserSkill("Linux", 11, "farzad"));
        skills.put("C", new UserSkill("C", 11, "farzad"));
        skills.put("Node.js", new UserSkill("Node.js", 11, "farzad"));
        String jobTitle = "دانشجو";
        String bio = "بیو ندارم";
        String password =  Hashing.sha256().hashString("12345678", StandardCharsets.UTF_8)
                .toString();
        String profilePictureUrl = "https://quera.ir/media/CACHE/images/public/avatars/7fec038a74bb45e6a6c3a229248ba7a5/15b5eb02454a49cefb753c491dd13ae4.jpg";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);


        id = "yasaman";
        firstName = "یاسمن";
        lastName = "جعفری";
        jobTitle = "دانشجو";
        bio = "بیو؟";
        skills.put("HTML", new UserSkill("HTML", 10, "yasaman"));
        skills.put("Javascript", new UserSkill("Javascript", 13, "yasaman"));
        skills.put("C++", new UserSkill("C++", 12, "yasaman"));
        skills.put("Java", new UserSkill("Java", 11, "yasaman"));
        skills.put("SQL", new UserSkill("SQL", 11, "yasaman"));
        skills.put("Linux", new UserSkill("Linux", 11, "yasaman"));
        skills.put("C", new UserSkill("C", 11, "yasaman"));
        skills.put("Node.js", new UserSkill("Node.js", 11, "yasaman"));
        password =  Hashing.sha256().hashString("12345678", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = "https://quera.ir/media/CACHE/images/public/avatars/22a4d7bc67bb46b7962d0e38a0b9a979/f13bf7290aecc1529de8b10f6d8c4cfa.jpg";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);


        id = "bahar";
        firstName = "بهار";
        lastName = "باطنی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "bahar"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "bahar"));
        skills.put("C++", new UserSkill("C++", 2, "bahar"));
        skills.put("Java", new UserSkill("Java", 3, "bahar"));
        jobTitle = "دانشجو";
        bio = "دنبال کار می‌گردم";
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = "";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

        id = "amir";
        firstName = "امیرحسین";
        lastName = "احمدی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "amir"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "amir"));
        skills.put("C++", new UserSkill("C++", 2, "amir"));
        skills.put("Java", new UserSkill("Java", 3, "amir"));
        jobTitle = "دانشجو";
        bio = "دنبال کار می‌گردم";
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = "";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

        id = "ghorbani";
        firstName = "علیرضا";
        lastName = "قربانی";
        skills = new HashMap<>();
        skills.put("HTML", new UserSkill("HTML", 5, "ghorbani"));
        skills.put("Javascript", new UserSkill("Javascript", 4, "ghorbani"));
        skills.put("C++", new UserSkill("C++", 2, "ghorbani"));
        skills.put("Java", new UserSkill("Java", 3, "ghorbani"));
        jobTitle = "خواننده";
        bio = "خوانندگی فایده نداشت دنبال کار توی حوزه‌ی آی‌تی هستم.";
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = "http://dl.parsnava.ir/dl/2017/07/Homayoun-Shajarian-Alireza-Ghorbani.jpg";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

        id = "sadaf";
        firstName = "صدف";
        lastName = "صادقیان";
        skills = new HashMap<>();
        skills.put("C", new UserSkill("C", 5, "sadaf"));
        skills.put("Javascript", new UserSkill("Javascript", 2, "sadaf"));
        skills.put("C++", new UserSkill("C++", 4, "sadaf"));
        skills.put("Java", new UserSkill("Python", 6, "sadaf"));
        skills.put("HTML", new UserSkill("HTML", 1, "sadaf"));
        jobTitle = "دانشجو";
        bio = "در جستجوی دیتا";
        password =  Hashing.sha256().hashString("123", StandardCharsets.UTF_8)
                .toString();
        profilePictureUrl = "";
        storeUserData(id, skills, firstName, lastName, jobTitle, bio, profilePictureUrl, password);

    }

}
