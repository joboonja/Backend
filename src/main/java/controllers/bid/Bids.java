package controllers.bid;

import controllers.bid.requests.AddBidRequest;
import exceptions.AlreadyBid;
import exceptions.InvalidBidRequirements;
import exceptions.ProjectNotFound;
import exceptions.UserNotFound;
import models.data.bid.Bid;
import models.data.bid.mapper.BidMapper;
import org.springframework.web.bind.annotation.*;


@RestController
public class Bids {
    @RequestMapping(value = "/bids", method = RequestMethod.POST)
    public Bid addBid(@RequestBody final AddBidRequest request,
                      @RequestAttribute("id") String userId)
            throws AlreadyBid, InvalidBidRequirements, UserNotFound, ProjectNotFound
    {
        Bid newBid = new Bid(userId, request.getProjectID(), request.getBidAmount());
        BidMapper.getInstance().addNewBid(newBid);
        return newBid;
    }
}
