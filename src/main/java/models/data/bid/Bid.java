package models.data.bid;


import models.data.project.Project;

public class Bid {
    private String biddingUserName;
    private Project project;
    private long offer;

    public Bid(String biddingUserID, Project project, long offer){
        this.biddingUserName = biddingUserID;
        this.project = project;
        this.offer = offer;
    }
    public long getOffer()
    {
        return offer;
    }

    public String getProjectID() { return project.getID(); }

    public String getBiddingUserName()
    {
        return biddingUserName;
    }


}
