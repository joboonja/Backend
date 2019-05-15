package exceptions;

import config.ExceptionsConfig;

public class InvalidUser extends ServerException {
    public InvalidUser(){
        super(ExceptionsConfig.InvalidUserStatus, ExceptionsConfig.InvalidUserMsg);
    }
}
