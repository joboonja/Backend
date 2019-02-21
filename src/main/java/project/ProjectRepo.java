package project;

import config.ProjectConfig;

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

    public Project getProjectByProjectID(String projectID) throws Exception
    {
        for (Project project : projects)
        {
            if (project.getID().equals(projectID))
                return project;
        }
        throw new Exception(ProjectConfig.PROJECT_NOT_FOUND_ERROR);
    }

    public void addNewProject(Project newProject)
    {
        for (Project project : projects)
            if (project.getTitle().equals(project.getTitle())){
                System.out.println(ProjectConfig.PROJECT_TITLE_ALREADY_EXISTS_ERROR);
                return;
            }
        projects.add(newProject);
    }
    public void addNewProjects(ArrayList<Project> projects)
    {
        projects.addAll(projects);
    }

}
