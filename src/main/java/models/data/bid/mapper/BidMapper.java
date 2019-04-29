package models.data.bid.mapper;

import exceptions.AlreadyBid;
import exceptions.InvalidBidRequirements;
import exceptions.ProjectNotFound;
import exceptions.UserNotFound;
import models.data.bid.Bid;
import models.data.mapper.Mapper;
import models.data.project.Project;
import models.data.project.ProjectRepo;
import models.data.user.User;
import models.data.user.mapper.UserMapper;

import java.util.ArrayList;

public class BidMapper extends Mapper<Bid, String> {
    private static BidMapper ourInstance = new BidMapper();
    private ArrayList<Bid> bids;

    public static BidMapper getInstance() {
        return ourInstance;
    }

    private BidMapper() {
        bids = new ArrayList<Bid>();
    }

    public boolean hasAlreadyBid(Bid newBid) {
        ArrayList<Bid> bids = this.getBidsOfProject(newBid.getProjectID());
        for(Bid bid : bids) {
            if(bid.getBiddingUserName().equals(newBid.getBiddingUserName()))
                return true;
        }
        return false;
    }

    private boolean isValidToAdd(Bid newBid) throws UserNotFound, ProjectNotFound {
        UserMapper userMapper = UserMapper.getInstance();
        ProjectRepo projectRepo = ProjectRepo.getInstance();

        User user = userMapper.getUserById(newBid.getBiddingUserName());
        Project project = projectRepo.getProjectByProjectID(newBid.getProjectID());

        return project.checkSkillSatisfaction(user.getSkills()) && project.checkBudgetSatisfaction(newBid.getOffer());
    }
    public void addNewBid(Bid newBid) throws AlreadyBid, InvalidBidRequirements, UserNotFound, ProjectNotFound {
        if(hasAlreadyBid(newBid))
            throw new AlreadyBid();
        if(!isValidToAdd(newBid))
            throw new InvalidBidRequirements();
        bids.add(newBid);
    }
    public ArrayList<Bid> getBidsOfProject(String projectID)
    {
        ArrayList<Bid> bidsOfProject = new ArrayList<Bid>();
        for(Bid bid : bids)
        {
            if(bid.getProjectID().equals(projectID))
                bidsOfProject.add(bid);
        }
        return bidsOfProject;
    }
    public boolean isEmpty()
    {
        return bids.size() == 0;
    }

    @Override
    protected String getCreateTableStatement() {
        return "";
    }
}
