package models.data.mapper;

import models.data.connectionPool.ConnectionPool;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T, I> implements IMapper<T, I> {

    protected Map<I, T> loadedMap = new HashMap<>();

    abstract protected String getFindStatement();
    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;

    public T find(I id) throws SQLException {
        T result = loadedMap.get(id);
        if (result != null)
            return result;

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getFindStatement())
        ) {
            stmt.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = stmt.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
}
