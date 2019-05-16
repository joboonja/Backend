package exceptions;

import config.ExceptionsConfig;

public class notAuthorized extends ServerException {
    public notAuthorized() {
        super(ExceptionsConfig.NotAuthStatus, ExceptionsConfig.NotAuthMsg);
    }
}
