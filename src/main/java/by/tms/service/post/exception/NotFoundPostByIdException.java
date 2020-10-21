package by.tms.service.post.exception;

public class NotFoundPostByIdException extends RuntimeException {
    public NotFoundPostByIdException() {
    }

    public NotFoundPostByIdException(String message) {
        super(message);
    }

    public NotFoundPostByIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundPostByIdException(Throwable cause) {
        super(cause);
    }

    public NotFoundPostByIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return "Post with such id does not exist!";
    }
}
