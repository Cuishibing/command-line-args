package cui.shibing.argvresolver.exception;

import cui.shibing.argvresolver.Option;

public class ResolveException extends Exception{
    public static enum ERROR {
        NO_ARGS,
        UN_KNOWN_OPTION,
        MISMATCH_OPTION_NUM
    }
    private Option option;
    private ERROR error;
    public ResolveException() {
    }

    public ResolveException(String message) {
        super(message);
    }

    public ResolveException(String message,ERROR error,Option option) {
        super(message);
        this.error = error;
        this.option = option;
    }

    public ERROR getError() {
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
    }
}
