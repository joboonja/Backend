package models.data.user.mapper;

import models.data.mapper.IMapper;
import models.data.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserMapper extends IMapper<User, String> {
        ArrayList <User> getAllUsers() throws SQLException;
        void insert(User user) throws SQLException;
        ArrayList<User> searchByName(String query) throws SQLException;
        String getValidateUserStatement();
        boolean validateUser(String id, String password) throws SQLException;
        void fillValidateUserStatement(PreparedStatement st, String id, String pass) throws SQLException;
}
