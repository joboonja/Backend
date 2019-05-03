package models.data.skill.mapper.UserSkillMapper;

import config.DatabaseColumns;
import models.data.mapper.Mapper;
import models.data.skill.Skill;
import models.data.skill.UserSkill;

import java.sql.*;
import java.util.ArrayList;

public class UserSkillMapper extends Mapper<UserSkill, String> implements IUserSkillMapper {
    private static UserSkillMapper ourInstance = new UserSkillMapper();
    private ArrayList<UserSkill> skills;

    public static UserSkillMapper getInstance() {
        return ourInstance;
    }

    private UserSkillMapper() {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        skills = new ArrayList<>();
    }


    public void addNewSkills(ArrayList<UserSkill> skillsToAdd)
    {
        skills.addAll(skillsToAdd);
    }

    public boolean contains(String skillName) {
        for (Skill skill :
                skills) {
            if (skill.getName().equals(skillName))
                return true;
        }
        return false;
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
                rs.getString(1),
                rs.getInt(3),
                rs.getString(2)
        );
    }

    private String getUserSkillsStatement() {
        return "SELECT " + DatabaseColumns.USER_SKILL +
                "FROM UserSkill S" +
                "WHERE S.usid = ?";
    }

    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();
        String stmt = "CREATE TABLE IF NOT EXISTS UserSkill(" +
                "usid CHAR(20)," +
                "points INTEGER," +
                "name CHAR(20)," +
                "PRIMARY KEY(name, usid)," +
                "FOREIGN KEY(name) REFERENCES Skill ON DELETE CASCADE" +
                ");";
        statements.add(stmt);
        return statements;
    }

    @Override
    public String getInsertStatement() {
        return "INSERT OR IGNORE INTO UserSkill (usid, points, name) " +
                "VALUES(? , ?, ?) ";
    }

    @Override
    public void fillInsertStatement(PreparedStatement stmt, UserSkill object) throws SQLException {
        stmt.setString(1, object.getUserId());
        stmt.setLong(2, object.getPoints());
        stmt.setString(3, object.getName());
    }
}
