package models.data.bid;


import models.data.project.Project;

public class Bid {
    private String biddingUserName;
    private Project project;
    private long offer;

    public Bid(String _biddingUserName, Project project, long offer){
        this.biddingUserName = _biddingUserName;
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
