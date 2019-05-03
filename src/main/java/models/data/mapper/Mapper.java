package models.data.mapper;

import exceptions.ProjectNotFound;
import models.data.connectionPool.ConnectionPool;
import models.data.project.Project;
import models.data.skill.UserSkill;

import java.sql.*;
import java.util.ArrayList;

public abstract class Mapper<T, I> implements IMapper<T, I> {

    abstract protected String getFindStatement();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;
    public T find(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getFindStatement())
        ) {
            stmt.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = stmt.executeQuery();
                if(!resultSet.next())
                    return null;
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    public ArrayList<T> findListForUser(I id, String query) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)
        ) {
            stmt.setString(1, id.toString());
            ArrayList<T> result = new ArrayList<>();
            ResultSet resultSet;
            resultSet = stmt.executeQuery();
            while(resultSet.next())
            {
                result.add(convertResultSetToDomainModel(resultSet));
            }
        return result;
        }
    }


    abstract protected ArrayList<String> getCreateTableStatement();
    public void createTable() throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             Statement stmt = con.createStatement();
        ) {
            try {
                for (String stmtString :
                        getCreateTableStatement()) {
                    stmt.addBatch(stmtString);
                }
                stmt.executeBatch();
            } catch (SQLException ex) {
                System.out.println();
                throw ex;
            }
        }
    }

    abstract String getInsertStatement();
    abstract void fillInsertStatement(PreparedStatement stmt, T object);
    public void insert(T object) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getInsertStatement())
        ) {
            fillInsertStatement(stmt, object);
            stmt.execute();
        }
    }
}
