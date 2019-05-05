package models.data.project.mapper;

import config.DatabaseColumns;
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

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper{
    private static ProjectMapper ourInstance = new ProjectMapper();

    public static ProjectMapper getInstance() {
        return ourInstance;
    }

    private ProjectMapper() {
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
        for (Project newProject:
             newProjects) {
            insert(newProject);
        }
    }

    public ArrayList<Project> getProjectsForUser(String userId, int pageNumber, int pageSize) throws UserNotFound {
        try {
            return findUserProjects(userId, pageNumber, pageSize);
        } catch (SQLException e) {
            throw new UserNotFound();
        }
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
        return "SELECT " + DatabaseColumns.PROJECT_FIND_COLOUMNS("Project")+ " " +
                "FROM Project , ProjectRequires " +
                "WHERE Project.pid = ?" +
                ";";
    }
    private String getFindRequirementsStatement()
    {
        return "SELECT " + DatabaseColumns.PROJECT_REQ_FIND_COLOUMNS("R") + " " +
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
                skills.put(rs.getString(1),  new UserSkill(rs.getString(1), rs.getInt(2)));
            }
            return skills;
        }
    }

    private String getSearchByDescriptionOrNameStmt()
    {
        return "SELECT DISTINCT " + DatabaseColumns.PROJECT_FIND_COLOUMNS("Projects") + " " +
                "FROM ( " + getFindByUserIdStatement() + " ) AS Projects " +
                "WHERE Projects.projectDescription LIKE ? OR " +
                "Projects.title LIKE ? " +
                "ORDER BY creationDate DESC ";
    }

    @Override
    public ArrayList<Project> searchByDescriptionOrName(String query, String userId, int pageNumber, int pageSize)
            throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     getSearchByDescriptionOrNameStmt() + getPaginationStatement(pageNumber, pageSize))
        ) {
            stmt.setString(1, userId);
            stmt.setString(2, "%" + query + "%");
            stmt.setString(3, "%" + query + "%");
            ArrayList<Project> result = new ArrayList<>();
            ResultSet resultSet;
            resultSet = stmt.executeQuery();
            while(resultSet.next())
                result.add(convertResultSetToDomainModel(resultSet));
            return result;
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
    public String getInsertStatement() {
        return "INSERT INTO Project ( creationDate, pid, title, imageUrl, projectDescription," +
                "budget, deadline) " +
                "VALUES(? , ?, ?, ?, ?, ?, ?) ";
    }

    @Override
    public void fillInsertStatement(PreparedStatement stmt, Project project) throws SQLException {
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
        return "SELECT DISTINCT "+ DatabaseColumns.PROJECT_FIND_COLOUMNS("P") +" " +
                "FROM Project P, ProjectRequires R " +
                "WHERE R.pid = P.pid AND NOT EXISTS ( SELECT * " +
                "                 FROM ProjectRequires R1 " +
                "                 WHERE P.pid = R1.pid " +
                "                AND NOT EXISTS ( SELECT * " +
                "                                    FROM UserSkill L " +
                "                                    WHERE L.usid = ? AND R1.points <= L.points AND " +
                "                                    L.name = R1.name " +
                "                " +
                ")) " +
                "ORDER BY creationDate DESC ";
    }

    @Override
    public ArrayList<Project> findUserProjects(String userId, int pageNumber, int pageSize) throws SQLException{
       return findListForUser(userId,
               getFindByUserIdStatement() + getPaginationStatement(pageNumber, pageSize));
    }

}
