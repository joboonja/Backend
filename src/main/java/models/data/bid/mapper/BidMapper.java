package models.data.bid.mapper;

import config.DatabaseErrorsConfig;
import exceptions.AlreadyBid;
import exceptions.InvalidBidRequirements;
import exceptions.ProjectNotFound;
import exceptions.UserNotFound;
import models.data.bid.Bid;
import models.data.connectionPool.ConnectionPool;
import models.data.mapper.Mapper;
import models.data.project.Project;
import models.data.project.mapper.ProjectMapper;
import models.data.user.User;
import models.data.user.mapper.UserMapper;
import sun.jvm.hotspot.ui.tree.SimpleTreeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BidMapper extends Mapper<Bid, String> implements IBidMapper {
    private static BidMapper ourInstance = new BidMapper();

    public static BidMapper getInstance() {
        return ourInstance;
    }

    private BidMapper() {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(DatabaseErrorsConfig.canNotCreateTable("Bid"));
        }
    }

    private String getHasAlreadyBidStatement()
    {
        return "SELECT * " +
                "FROM Bid, Project " +
                "WHERE Bid.userId = ? AND Bid.pid = Project.pid AND Project.pid = ? ";
    }

    @Override
    public boolean hasAlreadyBid(Bid newBid) throws SQLException{
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getHasAlreadyBidStatement())
        ) {
            stmt.setString(1, newBid.getBiddingUserName());
            stmt.setString(2, newBid.getProjectID());
            ResultSet resultSet;
            resultSet = stmt.executeQuery();
            boolean alreadyBid = resultSet.next();
            resultSet.close();
            stmt.close();
            con.close();
            return alreadyBid;
        }
    }

    private String getHasBidOnProjectStatement()
    {
        return "SELECT * " +
                "FROM Bid " +
                "WHERE Bid.pid = ? AND Bid.userId = ? ;";
    }
    @Override
    public boolean hasBidOnProject(String projectId, String userId) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(getHasBidOnProjectStatement())
        ) {
            stmt.setString(1, projectId);
            stmt.setString(2, userId);
            ResultSet resultSet;
            resultSet = stmt.executeQuery();
            boolean hasBid = resultSet.next();
            resultSet.close();
            stmt.close();
            con.close();
            return hasBid;
        }
    }

    @Override
    public boolean isValidToAdd(Bid newBid) throws UserNotFound, ProjectNotFound, SQLException {
        ProjectMapper projectMapper = ProjectMapper.getInstance();
        UserMapper userMapper = UserMapper.getInstance();
        ProjectMapper projectMapper = ProjectMapper.getInstance();
        Project project = projectMapper.getProjectByProjectID(newBid.getProjectID());
        User user = userMapper.getUserById(newBid.getBiddingUserName());

        return project.checkSkillSatisfaction(user.getSkills()) && project.checkBudgetSatisfaction(newBid.getOffer());
    }

    public void addNewBid(Bid newBid) throws AlreadyBid, InvalidBidRequirements, UserNotFound, ProjectNotFound {
        try {
            if (hasAlreadyBid(newBid))
                throw new AlreadyBid();
            if (!isValidToAdd(newBid))
                throw new InvalidBidRequirements();
            insert(newBid);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected String getFindStatement() {
        return "SELECT offer, userId, pid" +
                "FROM Bid, Project " +
                "WHERE Bid.pid = Project.pid AND Project.pid = ?;";
    }

    @Override
    protected Bid convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Bid(rs.getString(2), rs.getString(3), rs.getLong(1));
    }


    @Override
    protected ArrayList<String> getCreateTableStatement() {
        ArrayList<String> statements = new ArrayList<>();
        statements.add ("CREATE TABLE IF NOT EXISTS Bid(" +
                "    offer BIGINT," +
                "    userId CHAR(20) NOT NULL," +
                "    pid CHAR(20) NOT NULL," +
                "    FOREIGN KEY(userId) REFERENCES JoboonjaUser ON DELETE CASCADE," +
                "    FOREIGN KEY(pid) REFERENCES Project ON DELETE CASCADE," +
                "    PRIMARY KEY(offer, userId, pid)" +
                ");");
        return statements;
    }

    @Override
    public String getInsertStatement() {
        return "INSERT INTO Bid(offer, userId, pid) " +
                "VALUES(?, ?, ?);";
    }

    @Override
    public void fillInsertStatement(PreparedStatement stmt, Bid object) throws SQLException {
        stmt.setLong(1, object.getOffer());
        stmt.setString(2, object.getBiddingUserName());
        stmt.setString(3, object.getProjectID());
    }
}
