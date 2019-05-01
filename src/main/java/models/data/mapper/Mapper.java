package models.data.mapper;

import exceptions.ProjectNotFound;
import models.data.connectionPool.ConnectionPool;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    abstract protected String getDeleteStatement();
    public void delete(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getDeleteStatement())
        ) {
            stmt.setString(1, id.toString());
            try {
                stmt.execute();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.deleteByID query.");
                throw ex;
            }
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
}
