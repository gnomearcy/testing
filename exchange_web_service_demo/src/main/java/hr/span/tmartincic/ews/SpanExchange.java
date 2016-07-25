package hr.span.tmartincic.ews;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;
import org.ksoap2.transport.Transport;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SpanExchange extends ActionBarActivity
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

    String encodedCredentials;
    String username = "msa@span.eu";
    String password = "P@ssw0rd4ASM!";

    private TextView tv;
    private String res = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_exchange);

        tv = (TextView)findViewById(R.id.span_exchange_tv);

        myAsyncTask myRequest = new myAsyncTask();
//        provideAuthentication(username, password);
        myRequest.execute();
    }

    public void provideAuthentication(String username, String pw){
        String full = username+":"+pw;
//        byte[] encoded = Base64.encodeBase64(full.getBytes());
//        encodedCredentials = new String(encoded);
    }
    private List addAuthentication(){
        List headers = new ArrayList();
        headers.add(new HeaderProperty("Authorization", encodedCredentials));
        return headers;
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            tv.setText(res);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//
////            PropertyInfo requestServerVersion = new PropertyInfo();
////            requestServerVersion.setName(REQUEST_SERVER_VERSION);
//////            request.addProperty("Exchange", "");
////            AttributeInfo attributeInfo = new AttributeInfo();
////            attributeInfo.setElementType(requestServerVersion);
////            request.addAttribute(attributeInfo);
//
//            request.addProperty(REQUEST_SERVER_VERSION, EXCHANGE_VERSION);
//            request.addProperty(MAILBOX_CULTURE, "");
//            request.addProperty(EXCHANGE_IMPERSONATION, "");
//
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(request);
//
//            HttpTransportSE httpTransport = new HttpTransportSE(URL);
//
//            httpTransport.debug = true;
//
//            try {
//                httpTransport.call(SOAP_ACTION, envelope);
//                Log.d(tag, "Body: " + httpTransport.requestDump);
//            } catch (HttpResponseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (XmlPullParserException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
////            SoapObject response = null;
////            try
////            {
////                response = (SoapObject) envelope.bodyIn;
////            }
////            catch (Exception e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
////
////            Log.d(tag, "Response - " + response.toString());
////            res = response.toString();
//
//            return null;

            Log.d(tag, "Do in background?");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            //Parent SOAP object -> method name
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addAttribute(XMLNS, XMLNS_MESSAGES);
            request.addAttribute(XMLNS, XMLNS_TYPES);

            //add headers
            Element h = new Element().createElement(NAMESPACE, HEADER_REQUEST_SERVER_VERSION);
            h.setName(HEADER_REQUEST_SERVER_VERSION);
//            h.setAttribute(NAMESPACE, ATTRIBUTE_VERSION, EXCHANGE_SERVER_VERSION);
            h.setPrefix("t", NAMESPACE);
            h.setNamespace(NAMESPACE);
            h.setAttribute("", ATTRIBUTE_VERSION, EXCHANGE_SERVER_VERSION);

            envelope.headerOut = new Element[]{ h };
            envelope.dotNet = false;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);


//            Log.d(tag, "Envelope: " + envelope.bodyOut);
//            Log.d(tag, "-----------------------------");
            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            httpTransport.debug = true;

            SSLConnection.allowAllSSL();

            try
            {
                Log.d(tag, "About to call?");
//                httpTransport.call(SOAP_ACTION, envelope, addAuthentication());
                httpTransport.call(SOAP_ACTION, envelope);
                Log.d(tag, "Request: " + httpTransport.requestDump);
            }
            catch(HttpResponseException ex)
            {

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (XmlPullParserException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        private static final String HEADER_REQUEST_SERVER_VERSION = "RequestServerVersion";
        private static final String ATTRIBUTE_VERSION = "Version";
        private static final String EXCHANGE_SERVER_VERSION = "Exchange2013_SP1";
    }


}
