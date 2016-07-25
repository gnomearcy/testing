package hr.span.tmartincic.roomassistant.models;

public class EmptyArrayException extends Exception
{
    public EmptyArrayException() {
    }

    public EmptyArrayException(String detailMessage) {
        super(detailMessage);
    }

    public EmptyArrayException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    public EmptyArrayException(Throwable throwable) {
        super(throwable);
    }
}
