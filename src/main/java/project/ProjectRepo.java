package project;

import config.JoboonjaConfig;
import config.ProjectConfig;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectRepo {
    private static ProjectRepo ourInstance = new ProjectRepo();
    private ArrayList<Project> projects ;

    public static ProjectRepo getInstance() {
        return ourInstance;
    }

    private ProjectRepo() {
        projects = new ArrayList<Project>();
    }

    public Project getProjectByProjectTitle(String projectTitle) throws Exception
    {
        for (Project project : projects)
        {
            if (project.getTitle().equals(projectTitle))
                return project;
        }
        throw new Exception(JoboonjaConfig.PROJECT_NOT_FOUND_ERROR);
    }

    public void addNewProject(Project newProject)
    {
        for (Project project : projects)
            if (project.getTitle().equals(project.getTitle())){
                System.out.println(JoboonjaConfig.PROJECT_TITLE_ALREADY_EXISTS_ERROR);
                return;
            }
        projects.add(newProject);
    }

}
