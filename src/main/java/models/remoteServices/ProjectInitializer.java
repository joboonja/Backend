package models.remoteServices;


import config.ProjectConfig;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import tools.HttpRequest;
import tools.JSONDecoder;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectInitializer implements Runnable{
    private static ArrayList<Project> getProjects()
    {
        String projectsInfo = null;
        try {
            projectsInfo = HttpRequest.getRemoteData(ProjectConfig.PROJECT_INIT_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONDecoder.decodeJSONListToProjectList(projectsInfo);
    }
    public static void initProjects()
    {
        ArrayList<Project> projects = getProjects();
        ProjectMapper projectMapper = ProjectMapper.getInstance();
        try {
            projectMapper.addNewProjects(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        initProjects();
    }
}
