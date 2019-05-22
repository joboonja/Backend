package controllers;

import models.data.bid.mapper.BidMapper;
import models.data.user.mapper.UserMapper;
import models.remoteServices.ProjectInitializer;
import models.remoteServices.SkillInitializer;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Component
public class Initializer {
    @PostConstruct
    public void init() throws SQLException {
        SkillInitializer.initSkills();
        UserMapper.getInstance().addDefaultUser();
        ProjectInitializer.initProjects();
        BidMapper.getInstance();
    }
}


