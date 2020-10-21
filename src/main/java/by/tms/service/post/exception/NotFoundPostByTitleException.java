package by.tms.service.post.exception;

public class NotFoundPostByTitleException extends RuntimeException {
    public NotFoundPostByTitleException() {
    }

    public NotFoundPostByTitleException(String message) {
        super(message);
    }

    public NotFoundPostByTitleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundPostByTitleException(Throwable cause) {
        super(cause);
    }

    public NotFoundPostByTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return "Post with such title does not exist!";
    }
}
