package models.data.project.mapper;

import config.ProjectConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.ProjectNotFound;
import exceptions.UserNotFound;
import models.data.connectionPool.ConnectionPool;
import models.data.mapper.Mapper;
import models.data.project.Project;
import models.data.skill.UserSkill;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper{
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
                "    points INTEGER," +
                "    name CHAR(20)," +
                "    pid CHAR(20)," +
                "    FOREIGN KEY(name) REFERENCES Skill ON DELETE CASCADE," +
                "    FOREIGN KEY(pid) REFERENCES Project ON DELETE CASCADE," +
                "    PRIMARY KEY(points, name, pid)" +
                ");");
        return statements;
    }

    private String getInsertStatement() {
        return "INSERT INTO Project ( creationDate, pid, title, imageUrl, projectDescription," +
                "budget, deadline) " +
                "VALUES(? , ?, ?, ?, ?, ?, ?) ";
    }

    private void fillInsertStatement(PreparedStatement stmt, Project project) throws SQLException {
        stmt.setLong(1, project.getCreationDate());
        stmt.setString(2, project.getID());
        stmt.setString(3, project.getTitle());
        stmt.setString(4, project.getImageURL());
        stmt.setString(5, project.getDescription());
        stmt.setLong(6, project.getBudget());
        stmt.setLong(7, project.getDeadline());
    }

    private String getRequireInsertStatement() {
        return "INSERT INTO ProjectRequires(points, name, pid)" +
                "VALUES(?, ?, ?)";
    }

    private void fillInsertRequireStatement(PreparedStatement stmt, UserSkill userSkill, Project project) throws SQLException{
        stmt.setInt(1, userSkill.getPoints());
        stmt.setString(2, userSkill.getName());
        stmt.setString(3, project.getID());
    }
    @Override
    public void insert(Project project) throws SQLException {
        //TODO : after adding find
//        if(find(project.getID()) != null)
//            return;
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement pStmt = con.prepareStatement(getInsertStatement());
             PreparedStatement rStmt = con.prepareStatement(getRequireInsertStatement())
        ) {
            try {
                fillInsertStatement(pStmt, project);
                pStmt.execute();

                con.setAutoCommit(false);
                for(UserSkill userSkill : project.getSkills().values())
                {
                    fillInsertRequireStatement(rStmt, userSkill, project);
                    rStmt.addBatch();
                }
                rStmt.executeBatch();
                con.commit();

            } catch (SQLException ex) {
                System.out.println("error in Mapper.deleteByID query.");
                throw ex;
            }
        }
    }
}
