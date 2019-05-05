package exceptions;

import config.ExceptionsConfig;

public class DataBaseError extends ServerException {
    public DataBaseError() {
        super(ExceptionsConfig.DataBaseErrorStatus, ExceptionsConfig.DataBaseErrorMsg);
    }
}
