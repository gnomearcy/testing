package hr.span.tmartincic.ews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
//import org.ksoap2.transport.HttpTransportSE;
import hr.span.tmartincic.ews.defaultwithntlm.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class EWSDemo extends ActionBarActivity
{
//    SoapSerializationEnvelope envelope;
//    SoapObject request;
//    HttpTransportSE ht;
//    SoapPrimitive resultsString;

//    private static final String xmlVersionTag = "\"<!--?xml version=\\\"1.0\\\" encoding= \\\"UTF-8\\\" ?-->\"";
    private static final String SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius";
    private static final String tag = "ksoap2";
    private static final String NAMESPACE = "http://www.w3schools.com/webservices/";
    private static final String MAIN_REQUEST_URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
    String operationname = "FahrenheitToCelsius";
    String value = "50";

    int soapVersion = SoapEnvelope.VER11; //SOAP 1.1

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewsdemo);

        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    private SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request)
    {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(soapVersion);
//        envelope.dotNet = true;     //web service runs on .Net framework, so we have to set this flag
//        envelope.implicitTypes = true;
//        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
//        envelope.addMapping();

        return envelope;
    }

    private HttpTransportSE getHttpTransportSE()
    {
        HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
//        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY, MAIN_REQUEST_URL, 60000);
        ht.debug = true;        //record issues to LogCat
//        ht.setXmlVersionTag(xmlVersionTag);
        return ht;
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            SoapObject request = new SoapObject(NAMESPACE, operationname);
            request.addProperty("Fahrenheit", value);

            SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

            //doesn't add alias to xmlns namespace declarations which break the operation
            envelope.dotNet = true;
            HttpTransportSE ht = getHttpTransportSE();
            SoapObject res = null;

            try {
                ht.call(SOAP_ACTION, envelope);
                Log.d(tag, "Request dump - " + ht.requestDump);
//                SoapPrimitive respon = (SoapPrimitive) envelope.getResponse();
                SoapObject respon = (SoapObject) envelope.bodyIn; //object
                res = (SoapObject) envelope.bodyIn; //object

//                Log.d(tag, "Value - " + respon.getValue());

                String response = res.getProperty(0).toString();
                Log.d(tag, this.getClass().getSimpleName() + " " + response);
            }
            catch (XmlPullParserException q) {
                q.printStackTrace();
                Log.d(tag, q.getClass().getSimpleName() + " " +  q.getMessage());
            }
            catch (SoapFault q)
            {
                q.printStackTrace();
                Log.d(tag, q.getClass().getSimpleName() + " " + q.getMessage());
            }
            catch (HttpResponseException q)
            {
                q.printStackTrace();
                Log.d(tag, q.getClass().getSimpleName() + " " + q.getMessage());
            }
            catch (IOException q)
            {
                q.printStackTrace();
                Log.d(tag, q.getClass().getSimpleName() + " " + q.getMessage());
            }

            return null;
        }
    }

}
