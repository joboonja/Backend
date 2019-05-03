package models.data.skill.mapper;

import config.DatabaseColumns;
import models.data.connectionPool.ConnectionPool;
import models.data.mapper.Mapper;
import models.data.skill.Skill;
import models.data.skill.UserSkill;
import models.data.user.User;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SkillMapper extends Mapper<Skill, String> implements ISkillMapper {
    private static SkillMapper ourInstance = new SkillMapper();
    private ArrayList<Skill> skills;

    public static SkillMapper getInstance() {
        return ourInstance;
    }

    private SkillMapper() {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        skills = new ArrayList<Skill>();
    }


    public void addNewSkills(ArrayList<Skill> skillsToAdd)
    {
        skills.addAll(skillsToAdd);
    }

    public boolean contains(String skillName)
    {
        for (Skill skill :
                skills) {
            if(skill.getName().equals(skillName))
                return true;
        }
        return  false;
    }

    public ArrayList<Skill> getNotSubmittedSkills(String userId) throws SQLException {
        return findListForUser(userId, getNotSubmittedSkillsStatement());
    }

    @Override
    public void insertUserSkill(UserSkill userskill) throws SQLException {

    }

    @Override
    public String getInsertUserSkillStatement() {
        return "INSERT INTO UserSkill (usid, points, name) " +
                "VALUES(? , ?, ?) ";
    }

    public ArrayList<Skill> getUserSkills(String userId) throws SQLException {
        return findListForUser(userId, getUserSkillsStatement());
    }

    @Override
    protected String getFindStatement() {
        return null;
    }

    @Override
    protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    protected String getUserSkillsStatement() {
        return "SELECT " + DatabaseColumns.USER_SKILL +
                "FROM UserSkill S" +
                "WHERE S.usid = ?";
    }

    protected String getNotSubmittedSkillsStatement() {
        return "SELECT " + DatabaseColumns.USER_SKILL +
                "FROM Skill S " +
                "WHERE NOT EXISTS" +
                "(SELECT * " +
                "FROM UserSkill S1 " +
                "WHERE S1.usid = ? AND S.name = S1.name" +
                ")";
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<String>();
        String stmt1 = "CREATE TABLE IF NOT EXISTS Skill(" +
                "name CHAR(20)," +
                "PRIMARY KEY(name)" +
                ");";
        String stmt2 = "CREATE TABLE IF NOT EXISTS UserSkill(" +
                "usid CHAR(20)," +
                "points INTEGER," +
                "name CHAR(20)," +
                "PRIMARY KEY(name, usid)," +
                "FOREIGN KEY(name) REFERENCES Skill ON DELETE CASCADE" +
                ");";
        statements.add(stmt1);
        statements.add(stmt2);
        return statements;
    }
}
