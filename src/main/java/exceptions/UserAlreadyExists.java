package exceptions;

import config.ExceptionsConfig;

public class UserAlreadyExists extends ServerException {

    public UserAlreadyExists() {
        super(ExceptionsConfig.UserAlreadyExistsStatus, ExceptionsConfig.UserAlreadyExistsMsg);
    }
}
