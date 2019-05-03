package models.data.project.mapper;

import config.ProjectConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
        projects = new ArrayList<>();
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project getProjectByProjectID(String projectID) throws ProjectNotFound
    {
        Project project = null;
        try {
            project = find(projectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(project == null)
            throw new ProjectNotFound();
        return project;
    }
    public void addNewProjects(ArrayList<Project> newProjects) throws SQLException
    {
        projects.addAll(newProjects);
        for (Project newProject:
             projects) {
            insert(newProject);
        }
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

    @Override
    protected String getFindStatement() {
        return "SELECT " + ProjectConfig.PROJECT_FIND_COLOUMNS("Project")+ " " +
                "FROM Project , ProjectRequires " +
                "WHERE Project.pid = ?" +
                ";";
    }
    private String getFindRequirementsStatement()
    {
        return "SELECT " + ProjectConfig.PROJECT_REQ_FIND_COLOUMNS("R") + " " +
                "FROM Project P , ProjectRequires R " +
                "WHERE P.pid = R.pid AND P.pid = ? ";
    }
    @Override
    public HashMap<String, UserSkill> findProjectRequires(String projectId) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getFindRequirementsStatement());
        ) {
            stmt.setString(1, projectId);
            ResultSet rs;
            rs = stmt.executeQuery();
            HashMap<String, UserSkill> skills = new HashMap<>();
            while(rs.next())
            {
                skills.put(rs.getString(1), new UserSkill(rs.getString(1), rs.getInt(2)));
            }
            return skills;
        }
    }

    @Override
    protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        long creationDate = rs.getLong(1);
        String pid = rs.getString(2);
        String title = rs.getString(3);
        String imageUrl = rs.getString(4);
        String description = rs.getString(5);
        long budget = rs.getLong(6);
        long deadline = rs.getLong(7);
        HashMap<String, UserSkill> skills = findProjectRequires(pid);
        return new Project(pid, title, description, imageUrl, budget, skills, deadline, creationDate);
    }

    @Override
    protected String getDeleteStatement() {
        return null;
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
        if(find(project.getID()) != null)
            return;
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement pStmt = con.prepareStatement(getInsertStatement());
             PreparedStatement rStmt = con.prepareStatement(getRequireInsertStatement())
        ) {
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
        }
    }


    private String getFindByUserIdStatement()
    {
        return "SELECT "+ ProjectConfig.PROJECT_FIND_COLOUMNS("P") +"\n" +
                "FROM Project P, ProjectRequires R\n" +
                "WHERE R.pid = P.pid AND NOT EXISTS ( SELECT *\n" +
                "                 FROM ProjectRequires R1\n" +
                "                 WHERE P.pid = R1.pid\n" +
                "                AND NOT EXISTS ( SELECT *\n" +
                "                                    FROM UserSkill L\n" +
                "                                    WHERE L.usid = ? AND R1.points <= L.points AND " +
                "                                    L.name = R1.name\n" +
                "                ))\n";
    }
    @Override
    public ArrayList<Project> findUserProjects(String userId) throws SQLException{
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getFindByUserIdStatement())
        ) {
            stmt.setString(1, userId.toString());
            ArrayList<Project> projects = new ArrayList<>();
            ResultSet resultSet;
            resultSet = stmt.executeQuery();
            while(resultSet.next())
            {
                projects.add(convertResultSetToDomainModel(resultSet));
            }
            return projects;
        }
    }


}
