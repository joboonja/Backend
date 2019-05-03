package models.data.project.mapper;

import config.ProjectConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.ProjectNotFound;
import exceptions.UserNotFound;
import models.data.mapper.Mapper;
import models.data.project.Project;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

public class ProjectMapper extends Mapper<Project, String> {
    private static ProjectMapper ourInstance = new ProjectMapper();
    private ArrayList<Project> projects ;

    public static ProjectMapper getInstance() {
        return ourInstance;
    }

    private ProjectMapper() {
        projects = new ArrayList<Project>();
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project getProjectByProjectID(String projectID) throws ProjectNotFound
    {
        for (Project project : projects)
        {
            if (project.getID().equals(projectID))
                return project;
        }
        throw new ProjectNotFound();
    }
    public Project getProjectByIDForUser(String projectID, String userID) throws Exception
    {
        Project project = getProjectByProjectID(projectID);
        User user = UserMapper.getInstance().getUserById(userID);
        if(!project.checkSkillSatisfaction(user.getSkills()))
            throw new Exception(ProjectConfig.USER_CANNOT_SATISFY_PROJECT);
        return project;
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
    public void addNewProjects(ArrayList<Project> newProjects)
    {
        projects.addAll(newProjects);
    }

    public ArrayList<Project> getProjectsForUser(String userId) throws UserNotFound {
        ArrayList<Project> projectsForUser = new ArrayList<Project>();
        User user = UserMapper.getInstance().getUserById(userId);
        for(Project project : projects)
        {
            if(project.checkSkillSatisfaction(user.getSkills()))
            {
                projectsForUser.add(project);
            }
        }
        return projectsForUser;
    }

    @Override
    protected String getFindStatement() {
        return null;
    }

    @Override
    protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();
        statements.add("CREATE TABLE IF NOT EXISTS Project(" +
                "    creationDate BIGINT," +
                "    pid CHAR(20)," +
                "    title CHAR(20)," +
                "    imageUrl TEXT," +
                "    projectDescription TEXT," +
                "    budget BIGINT," +
                "    deadline BIGINT," +
                "    PRIMARY KEY(pid)" +
                ");");
        statements.add("CREATE TABLE IF NOT EXISTS ProjectRequires(" +
                "    usid CHAR(20)," +
                "    pid CHAR(20)," +
                "    FOREIGN KEY(usid) REFERENCES UserSkill ON DELETE CASCADE," +
                "    FOREIGN KEY(pid) REFERENCES Project ON DELETE CASCADE," +
                "    PRIMARY KEY(usid, pid)" +
                ");");
        return statements;
    }
}
