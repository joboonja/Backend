package models.data.skill.mapper.UserSkillMapper;

import config.DatabaseColumns;
import exceptions.DataBaseError;
import exceptions.DuplicateEndorse;
import config.ProjectServiceConfig;
import config.UserConfig;
import models.data.connectionPool.ConnectionPool;
import models.data.mapper.Mapper;
import models.data.skill.Skill;
import models.data.skill.UserSkill;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;

public class UserSkillMapper extends Mapper<UserSkill, String> implements IUserSkillMapper {
    private static UserSkillMapper ourInstance = new UserSkillMapper();

    public static UserSkillMapper getInstance() {
        return ourInstance;
    }

    private UserSkillMapper() {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserSkill(String name, String userId) {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getDeleteUserSkillStatement())
        ) {
            stmt.setString(1, name);
            stmt.setString(2, userId);
            try {
                stmt.execute();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDeleteUserSkillStatement() {
        return "DELETE FROM UserSkill " +
                "WHERE name = ? AND usid = ? ";
    }

    public ArrayList<UserSkill> getUserSkills(String userId) throws SQLException {
        return findListForUser(userId, getUserSkillsStatement());
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + DatabaseColumns.USER_SKILL +
                " FROM UserSkill" +
                " WHERE usid = ? AND name = ?";
    }

    @Override
    protected UserSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new UserSkill(
                rs.getString(2),
                rs.getInt(3),
                rs.getString(1)
        );
    }

    private String getUserSkillsStatement() {
        return "SELECT " + DatabaseColumns.USER_SKILL +
                "FROM UserSkill S " +
                "WHERE S.usid = ?";
    }

    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();
        String stmt = "CREATE TABLE IF NOT EXISTS UserSkill(" +
                "usid CHAR(40)," +
                "points INTEGER," +
                "name CHAR(40)," +
                "PRIMARY KEY(name, usid)," +
                "FOREIGN KEY(name) REFERENCES Skill(name) ON DELETE CASCADE" +
                ");";
        String stmt2 = "CREATE TABLE IF NOT EXISTS Endorsement(" +
                "userId CHAR(40)," +
                "name CHAR(40)," +
                "usid CHAR(40)," +
                "PRIMARY KEY(userId, name, usid)," +
                "FOREIGN KEY(userId) REFERENCES JoboonjaUser(userId) on DELETE CASCADE," +
                "FOREIGN KEY (name, usid) REFERENCES UserSkill(name, usid) on DELETE CASCADE" +
                ");";
        statements.add(stmt);
        statements.add(stmt2);
        return statements;
    }


    @Override
    public String getInsertStatement() {
        return "INSERT IGNORE INTO UserSkill (usid, points, name) " +
                "VALUES( ? , ? , ? ) ";
    }

    @Override
    public void fillInsertStatement(PreparedStatement stmt, UserSkill object) throws SQLException {
        stmt.setString(1, object.getUserId());
        stmt.setLong(2, object.getPoints());
        stmt.setString(3, object.getName());
    }


    private String getCanEndorseStatement()
    {
        return "SELECT * " +
                "FROM Endorsement E " +
                "WHERE E.userId = ? AND E.usid = ? AND E.name = ? ;";
    }
    @Override
    public boolean canEndorse(String endorserId, String endorsedId, String userSkillName) {
        try {
            Connection con = ConnectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement(getCanEndorseStatement());
            stmt.setString(1, endorserId);
            stmt.setString(2, endorsedId);
            stmt.setString(3, userSkillName);
            ResultSet resultSet = stmt.executeQuery();
            boolean can = !resultSet.next();
            resultSet.close();
            stmt.close();
            con.close();
            return can;
        }
        catch (SQLException e){
            return false;
        }
    }



    private String getCreateEndorseStatement()
    {
        return "INSERT INTO Endorsement (userId, name, usid) " +
                "VALUES (? , ? , ?) ";
    }
    private String getUpdateEndorseStatement()
    {
        return "UPDATE UserSkill " +
                "SET points = points + 1 " +
                "WHERE usid = ? AND name = ? ";
    }
    @Override
    public void endorse(String endorserId, String endorsedId, String userSkillName)
            throws DuplicateEndorse, DataBaseError, SQLException {
        if(!canEndorse(endorserId, endorsedId, userSkillName))
            throw new DuplicateEndorse();
        Connection con = ConnectionPool.getConnection();
        try (
             PreparedStatement createStmt = con.prepareStatement(getCreateEndorseStatement())
        )
        {
            con.setAutoCommit(false);
            createStmt.setString(1, endorserId);
            createStmt.setString(2, userSkillName);
            createStmt.setString(3, endorsedId);
            createStmt.execute();
            createStmt.close();

            PreparedStatement updateStmt = con.prepareStatement(getUpdateEndorseStatement());
            updateStmt.setString(1, endorsedId);
            updateStmt.setString(2, userSkillName);
            updateStmt.execute();
            updateStmt.close();
            con.commit();
            con.close();

        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
            throw new DataBaseError();
        }
    }

    @Override
    public boolean getEndorsedOrNot(String endorserId, String endorsedId, String userSkillName) {
        return !canEndorse(endorserId, endorsedId, userSkillName);
    }


}
