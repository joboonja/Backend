package remoteServices;


import config.ProjectConfig;
import org.json.JSONArray;
import project.Project;
import project.ProjectRepo;
import tools.HttpRequest;
import tools.JSONDecoder;

import java.util.ArrayList;

public class ProjectInitializer {
    private static ArrayList<Project> getProjects()
    {
        JSONArray projectsInfo = null;
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
        ProjectRepo projectRepo = ProjectRepo.getInstance();
        projectRepo.addNewProjects(projects);
    }
}
