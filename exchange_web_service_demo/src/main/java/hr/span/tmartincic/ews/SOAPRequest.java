package hr.span.tmartincic.ews;

/**
 *  Wrapper for custom request
 *  request - XML request string
 *  url - request end point
 */
public class SOAPRequest
{
    public final String request;
    public final String url;

    public SOAPRequest(String XMLRequest, String endUrl)
    {
        this.request = XMLRequest;
        this.url = endUrl;
    }
}
