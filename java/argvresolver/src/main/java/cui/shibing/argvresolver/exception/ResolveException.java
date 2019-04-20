package cui.shibing.argvresolver.exception;

import cui.shibing.argvresolver.Option;

public class ResolveException extends Exception{
    public enum ErrorType {
        NO_ARGS,
        MISMATCH_OPTION_NUM
    }
    private Option option;
    private ErrorType errorType;
    public ResolveException() {
    }

    public ResolveException(String message) {
        super(message);
    }

    public ResolveException(String message, ErrorType errorType, Option option) {
        super(message);
        this.errorType = errorType;
        this.option = option;
    }

    public Option getOption() {
        return option;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
