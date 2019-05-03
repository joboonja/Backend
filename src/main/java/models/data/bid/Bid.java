package models.data.bid;


import models.data.project.Project;

public class Bid {
    private String biddingUserName;
    private String projectID;
    private long offer;

    public Bid(String biddingUserID, String projectID, long offer){
        this.biddingUserName = biddingUserID;
        this.projectID = projectID;
        this.offer = offer;
    }
    public long getOffer()
    {
        return offer;
    }

    public String getProjectID() { return projectID; }

    public String getBiddingUserName()
    {
        return biddingUserName;
    }


}
