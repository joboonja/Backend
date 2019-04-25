package models.remoteServices;

import models.data.connectionPool.ConnectionPool;
import models.data.user.UserRepo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class Initializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SkillInitializer.initSkills();
        ProjectInitializer.initProjects();
        UserRepo.getInstance().addDefaultUser();
        try {
            Connection c = ConnectionPool.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}