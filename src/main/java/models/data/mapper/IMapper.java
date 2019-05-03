package models.data.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IMapper<T, I> {
    T find(I id) throws SQLException;
    ArrayList<T> findListForUser(I id, String query) throws SQLException;
    void insert(T object) throws SQLException;
}
