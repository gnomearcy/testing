package hr.span.tmartincic.ews;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;

//import org.apache.commons.codec.binary.Base64;


public class FindItemAct extends ActionBarActivity
{
    private static final String tag = "ksoap2";
    private static final String SOAP_ACTION = "http://schemas.microsoft.com/exchange/services/2006/messages/GetRoomLists";
    private static final String NAMESPACE = "https://eagle03.span.bay:444/EWS/Services.wsdl";
    private static final String METHOD_NAME = "GetRoomLists";
    private static final String URL = "https://eagle03.span.bay:444/EWS/Services.wsdl";

//    private static final String tag = "ksoap2";
//    private static final String SOAP_ACTION = "http://footballpool.dataaccess.eu/TopGoalScorers";
//    private static final String METHOD_NAME = "TopGoalScorers";
//    private static final String NAMESPACE = "http://footballpool.dataaccess.eu"; //host in POST request
//    private static final String URL = "http://footballpool.dataaccess.eu/data/info.wso?WSDL";

    //TODO ovaj atribut nema "tip" tip u types.xsd?
    private static final String EXCHANGE_IMPERSONATION = "ExchangeImpersonation";

    //TODO don't include these since they're empty?
    private static final String MAILBOX_CULTURE = "MailboxCulture";
    private static final String REQUEST_SERVER_VERSION = "RequestServerVersion";
    private static final String EXCHANGE_VERSION = "Exchange2013_SP1";
    private static final String PRINCIPAL_NAME = "msa@span.eu";

    private static final String XMLNS_MESSAGES = "http://schemas.microsoft.com/exchange/services/2006/messages";
    private static final String XMLNS_TYPES = "http://schemas.microsoft.com/exchange/services/2006/types";
    private static final String XMLNS = "xmlns";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_exchange);

        SOAPClient soapClient = new SOAPClient();
        soapClient.callService(w3cSoapRequest.getRequest(), new SOAPClient.OnServiceListener()
        {
            @Override
            public void onServiceSuccess(String responseXml)
            {
//                Log.d(tag, "Response - " + responseXml);
                logResponse(responseXml);

                //da li mogu napraviti soap objekat sad tu i dohvatiti property?
                SoapObject so = new SoapObject("","");
                so.setInnerText(responseXml);

                Log.d(tag, "Response as soap object - " + so.toString());
            }
        });
    }

    private void logResponse(String message)
    {
        int max = 4000;
        char[] responseChars = message.toCharArray();

        if(responseChars.length > max)
        {
            String s = new String(responseChars, 0, max);
            Log.d(tag, "First half length - " + s.length());
            Log.d(tag, "First half content - " + s);

            String s1 = new String(responseChars, max, responseChars.length - max);
            Log.d(tag, "Second half length - " + s1.length());
            Log.d(tag, "Second half content - " + s1);
        }
    }
}
