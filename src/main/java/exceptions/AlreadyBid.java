package exceptions;


import config.ExceptionsConfig;

public class AlreadyBid extends ServerException {
    public AlreadyBid() {
        super(ExceptionsConfig.AlreadyBidStatus, ExceptionsConfig.AlreadyBidMsg);
    }

}