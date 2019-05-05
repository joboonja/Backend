package models.data.user.mapper;

import models.data.mapper.IMapper;
import models.data.user.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserMapper extends IMapper<User, String> {
        ArrayList <User> getAllUsers() throws SQLException;
        void insert(User user) throws SQLException;
        ArrayList<User> searchByName(String query, int pageNumber, int pageSize) throws SQLException;
}
