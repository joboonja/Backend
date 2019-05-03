package models.data.user.mapper;

        import models.data.bid.Bid;
        import models.data.mapper.IMapper;
        import models.data.skill.UserSkill;
        import models.data.user.User;

        import java.sql.SQLException;
        import java.util.List;

public interface IUserMapper extends IMapper<User, String> {
        List<UserSkill> findUserSkills(String userId) throws SQLException;
        String getInsertStatement();
        void insert(User user) throws SQLException;
}
