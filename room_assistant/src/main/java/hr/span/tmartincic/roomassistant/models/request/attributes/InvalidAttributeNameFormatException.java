package hr.span.tmartincic.roomassistant.models.request.attributes;

/**
 * Created by tmartincic on 6/23/2015.
 */
public class InvalidAttributeNameFormatException extends Exception
{
    public InvalidAttributeNameFormatException() {
    }

    public InvalidAttributeNameFormatException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidAttributeNameFormatException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    public InvalidAttributeNameFormatException(Throwable throwable) {
        super(throwable);
    }
}
