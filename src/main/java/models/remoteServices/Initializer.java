package models.remoteServices;

import models.data.connectionPool.ConnectionPool;
import models.data.user.UserRepo;

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
        SkillInitializer.initSkills();
        ProjectInitializer.initProjects();
        UserRepo.getInstance().addDefaultUser();
        try {
            Statement stmt = null;
            Connection conn = ConnectionPool.getInstance().getConnection();
            stmt = conn.createStatement();

            String sql = "CREATE TABLE REGISTRATION " +
                    "(id INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}