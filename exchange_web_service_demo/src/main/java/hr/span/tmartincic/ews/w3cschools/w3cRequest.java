package hr.span.tmartincic.ews.w3cschools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import hr.span.tmartincic.ews.R;
import hr.span.tmartincic.ews.defaultwithntlm.HttpTransportSE;


public class w3cRequest extends ActionBarActivity
{
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
        envelope.setOutputSoapObject(request);

        return envelope;
    }

    private HttpTransportSE getHttpTransportSE()
    {
        HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
        ht.debug = true;        //record issues to LogCat
        return ht;
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            SoapObject request = new SoapObject(NAMESPACE, operationname);
//            request.addProperty("Fahrenheit", value);
            /**
             * <FahrenheitToCelsius xmlns="http://www.w3schools.com/webservices/">
             <Fahrenheit>50</Fahrenheit>
             </FahrenheitToCelsius>
             */
            FahrenheitToCelsius fahrenheitToCelsius = new FahrenheitToCelsius();
//            fahrenheitToCelsius.add();

            PropertyInfo fahrenheitToCelsiusPropertyInfo = new PropertyInfo();
            fahrenheitToCelsiusPropertyInfo.setName(FahrenheitToCelsius.class.getSimpleName());
            fahrenheitToCelsiusPropertyInfo.setValue(fahrenheitToCelsius);
            fahrenheitToCelsiusPropertyInfo.setType(fahrenheitToCelsius.getClass());

            request.addProperty(fahrenheitToCelsiusPropertyInfo);

            SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);
            envelope.addMapping(NAMESPACE, FahrenheitToCelsius.class.getSimpleName(), FahrenheitToCelsius.class);

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
