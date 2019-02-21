package bid;

import config.BidConfig;
import org.json.JSONObject;
import project.Project;
import project.ProjectRepo;
import user.User;
import user.UserRepo;

import java.util.ArrayList;

public class BidRepo {
    private static BidRepo ourInstance = new BidRepo();
    private ArrayList<Bid> bids;

    public static BidRepo getInstance() {
        return ourInstance;
    }

    private BidRepo() {
        bids = new ArrayList<Bid>();
    }

    private boolean isValidToAdd(Bid newBid) throws Exception
    {
        UserRepo userRepo = UserRepo.getInstance();
        ProjectRepo projectRepo = ProjectRepo.getInstance();

        User user = userRepo.getUserByUsername(newBid.getBiddingUserName());
        Project project = projectRepo.getProjectByProjectTitle(newBid.getProjectTitle());

        return project.checkSatisfaction(user.getSkills(), newBid.getOffer());
    }
    public void addNewBid(Bid newBid) {
        try{
            if(isValidToAdd(newBid))
                bids.add(newBid);
            else
                throw new Exception(BidConfig.BID_CREATE_ERROR);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<Bid> getBidsOfProject(String projectTitle)
    {
        ArrayList<Bid> bidsOfProject = new ArrayList<Bid>();
        for(Bid bid : bids)
        {
            if(bid.getProjectTitle().equals(projectTitle))
                bidsOfProject.add(bid);
        }
        return bidsOfProject;
    }
    public boolean isEmpty()
    {
        return bids.size() == 0;
    }





}
