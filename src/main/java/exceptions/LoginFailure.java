package exceptions;

import config.ExceptionsConfig;

public class LoginFailure extends ServerException {
    public LoginFailure(){
        super(ExceptionsConfig.LoginFailureStatus, ExceptionsConfig.LoginFailureMsg);
    }
}
