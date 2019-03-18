package exceptions;

import config.ExceptionsConfig;

public class UserNotFound extends ServerException {
    public UserNotFound() {
         super(ExceptionsConfig.UserNotFoundStatus, ExceptionsConfig.UserNotFoundMsg);
    }
}