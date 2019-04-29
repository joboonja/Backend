package models.remoteServices;

import models.data.bid.mapper.BidMapper;
import models.data.connectionPool.ConnectionPool;
import models.data.user.mapper.UserMapper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class Initializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BidMapper.getInstance();
        SkillInitializer.initSkills();
        ProjectInitializer.initProjects();
        UserMapper.getInstance().addDefaultUser();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}