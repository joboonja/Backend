package models.data.skill.mapper.SkillMapper;

import config.DatabaseColumns;
import models.data.mapper.Mapper;
import models.data.skill.Skill;

import java.sql.*;
import java.util.ArrayList;

public class SkillMapper extends Mapper<Skill, String> implements ISkillMapper {
    private static SkillMapper ourInstance = new SkillMapper();

    public static SkillMapper getInstance() {
        return ourInstance;
    }

    private SkillMapper() {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addNewSkills(ArrayList<Skill> skillsToAdd) throws SQLException {
        for(Skill skill : skillsToAdd) {
            insert(skill);
        }
    }

    public ArrayList<Skill> getNotSubmittedSkills(String userId) throws SQLException {
        return findListForUser(userId, getNotSubmittedSkillsStatement());
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + DatabaseColumns.SKILL +
                "FROM Skill S" +
                "WHERE S.name = ?";
    }

    @Override
    protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Skill(
                rs.getString(1)
        );
    }

    private String getNotSubmittedSkillsStatement() {
        return "SELECT " + DatabaseColumns.SKILL +
                "FROM Skill S " +
                "WHERE NOT EXISTS" +
                "(SELECT * " +
                "FROM UserSkill S1 " +
                "WHERE S1.usid = ? AND S.name = S1.name" +
                ")";
    }


    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();
        String stmt = "CREATE TABLE IF NOT EXISTS Skill(" +
                "name CHAR(40)," +
                "PRIMARY KEY(name)" +
                ");";
        statements.add(stmt);
        return statements;
    }

    @Override
    public String getInsertStatement() {
        return "INSERT IGNORE INTO Skill (name) " +
                "VALUES(?) ";
    }

    @Override
    public void fillInsertStatement(PreparedStatement stmt, Skill object) throws SQLException {
        stmt.setString(1, object.getName());
    }
}
