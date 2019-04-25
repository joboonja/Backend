package models.data.mapper;

import java.sql.SQLException;

public interface IMapper<T, I> {
//    T find(I id) throws SQLException;
    void createTable() throws SQLException;
}
