package controllers.bid.requests;

import java.io.Serializable;

public class AddBidRequest implements Serializable {
    private String projectID;
    private long bidAmount;

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
    public void setBidAmount(long bidAmount) { this.bidAmount = bidAmount; }

    public String getProjectID() {return projectID; }
    public long getBidAmount() {
        return bidAmount;
    }
}
