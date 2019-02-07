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

    public Bid(JSONObject bidInfo){
        biddingUserName = bidInfo.getString(BidConfig.BIDDING_USER);
        projectTitle = bidInfo.getString(BidConfig.PROJECT_TITLE);
        offer = bidInfo.getInt(BidConfig.BID_AMOUNT);
    }

    public long getOffer()
    {
        return offer;
    }

    public String getBiddingUserName()
    {
        return biddingUserName;
    }

}
