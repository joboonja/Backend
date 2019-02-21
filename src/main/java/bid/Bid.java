package bid;

import config.BidConfig;
import config.UserConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import project.Project;
import skill.Skill;
import user.User;

import java.util.ArrayList;

public class Bid {
    private String biddingUserName;
    private String projectTitle;
    private long offer;

    public Bid(String _biddingUserName, String _projectTitle, long _offer){
        biddingUserName = _biddingUserName;
        projectTitle = _projectTitle;
        offer = _offer;
    }
    public long getOffer()
    {
        return offer;
    }

    public String getProjectTitle() { return projectTitle; }

    public String getBiddingUserName()
    {
        return biddingUserName;
    }


}
