package hr.span.tmartincic.roomassistant;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.Transport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XTransport extends Transport
{
    private static final String encoding = "UTF-8";

    @Override
    public List call(String s, SoapEnvelope soapEnvelope, List list) throws IOException, XmlPullParserException
    {
        /* Stub */
        return null;
    }

    @Override
    public List call(String s, SoapEnvelope soapEnvelope, List list, File file) throws IOException, XmlPullParserException
    {
        /* Stub */
        return null;
    }

    @Override
    public ServiceConnection getServiceConnection() throws IOException
    {
        /* Stub */
        return null;
    }

    @Override
    protected byte[] createRequestData(SoapEnvelope envelope) throws IOException
    {
        return super.createRequestData(envelope, encoding);
    }

    public String getSoapRequestAsXml(SoapSerializationEnvelope envelope)
    {
        byte[] byteResult = null;

        try
        {
            byteResult = this.createRequestData(envelope);
            return new String(byteResult, encoding);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
