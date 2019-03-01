package models.remoteServices;

import models.data.user.UserRepo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SkillInitializer.initSkills();
        ProjectInitializer.initProjects();
        UserRepo.getInstance().addDefaultUser();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}