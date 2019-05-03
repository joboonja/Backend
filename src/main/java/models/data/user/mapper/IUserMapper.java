package models.data.user.mapper;

import models.data.mapper.IMapper;
import models.data.user.User;

import java.sql.SQLException;

public interface IUserMapper extends IMapper<User, String> {
//        List<UserSkill> findUserSkills(String userId) throws SQLException;
        String getInsertStatement();
        void insert(User user) throws SQLException;
}
