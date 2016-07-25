package hr.span.tmartincic.ews.jcifs_konekcija;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import hr.span.tmartincic.ews.R;
import hr.span.tmartincic.ews.ServiceInfo;
import hr.span.tmartincic.ews.defaultwithntlm.HttpTransportSE;
import jcifs.smb.NtlmPasswordAuthentication;

public class TestJcifsConnection extends ActionBarActivity
{
    private static final String tag = "ksoap2";
    public static final String serviceUrl = "https://eagle03.span.bay:444/EWS/Exchange.asmx";
    public static final String username = "msa@span.eu";
    public static final String password = "P@ssw0rd4ASM!";
    public static final String userDomain = "span";
    public static final String targetNamespace = "http://www.w3schools.com/webservices/";
    public static final String endPoint = "http://www.w3schools.com/webservices/tempconvert.asmx";

    private String request = "POST /webservices/tempconvert.asmx HTTP/1.1\n" +
            "Host: www.w3schools.com\n" +
            "Content-Type: text/xml; charset=utf-8\n" +
            "Content-Length: length\n" +
            "SOAPAction: \"http://www.w3schools.com/webservices/FahrenheitToCelsius\"\n" +
            "\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <FahrenheitToCelsius xmlns=\"http://www.w3schools.com/webservices/\">\n" +
            "      <Fahrenheit>50</Fahrenheit>\n" +
            "    </FahrenheitToCelsius>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jcifs_connection);

        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            //cisti url connection request
            try
            {
                URL url = new URL(endPoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                /*POST /webservices/tempconvert.asmx HTTP/1.1
                Host: www.w3schools.com
                Content-Type: text/xml; charset=utf-8
                Content-Length: length
                SOAPAction: "http://www.w3schools.com/webservices/FahrenheitToCelsius"*/

                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ServiceInfo.userDomain, ServiceInfo.username, ServiceInfo.password);

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.setRequestProperty("SOAPAction", "http://www.w3schools.com/webservices/FahrenheitToCelsius");

                conn.connect();

                int status = conn.getResponseCode();

                if(status != 200)
                {
                    Log.d(tag, "Bad request - code: " + status);
                }
                else
                {

                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
//            SoapObject request = new SoapObject(targetNamespace, "FahrenheitToCelsius");
//            request.addProperty("Fahrenheit", "5");

//            SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

            //doesn't add alias to xmlns namespace declarations which break the operation
//            envelope.dotNet = true;
//            HttpTransportSE ht = getHttpTransportSE();
//            SoapObject res = null;

//            try {
//                ht.call(SOAP_ACTION, envelope);
//                Log.d(tag, "Request dump - " + ht.requestDump);
//                SoapObject respon = (SoapObject) envelope.getResponse();
//                res = (SoapObject) envelope.bodyIn;
//                String response = res.getProperty(0).toString();
//                Log.d(tag, this.getClass().getSimpleName() + " " + response);
//            }
//            catch (XmlPullParserException q) {
//                q.printStackTrace();
//                Log.d(tag, q.getClass().getSimpleName() + " " +  q.getMessage());
//            }
//            catch (SoapFault q)
//            {
//                q.printStackTrace();
//                Log.d(tag, q.getClass().getSimpleName() + " " + q.getMessage());
//            }
//            catch (HttpResponseException q)
//            {
//                q.printStackTrace();
//                Log.d(tag, q.getClass().getSimpleName() + " " + q.getMessage());
//            }
//            catch (IOException q)
//            {
//                q.printStackTrace();
//                Log.d(tag, q.getClass().getSimpleName() + " " + q.getMessage());
//            }

            return null;
        }
    }
}
