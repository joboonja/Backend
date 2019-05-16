package exceptions;

import config.ExceptionsConfig;

public class EndorseSelf extends ServerException {
    public EndorseSelf(){
        super(ExceptionsConfig.EndorseSelfStatus, ExceptionsConfig.EndorseSelfMsg);
    }
}
