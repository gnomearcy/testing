package hr.span.tmartincic.ews.prepravljanje_ksoap2;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import hr.span.tmartincic.ews.FindItemSoapRequest;
import hr.span.tmartincic.ews.R;
import hr.span.tmartincic.ews.SOAPClient;

public class TestPrepravka extends ActionBarActivity
{
    private static final String tag = "ksoap2";
    private static String SOAPACTION = "http://schemas.microsoft.com/exchange/services/2006/messages/FindItem";
    private static String NAMESPACE = "http://schemas.microsoft.com/exchange/services/2006/messages";
    private static String OPERATION = "FindItem";
    private static String nesto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_prepravka);
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            SoapObject request = new SoapObject(NAMESPACE, OPERATION);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setAddAdornments(false);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            //parameter: xml request
            HttpTransportSE transport = new HttpTransportSE(FindItemSoapRequest.getFindItemRequestObject());
            transport.debug = true;
            try
            {
                transport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;

            }
            catch(Exception e)
            {
                Log.d(tag, e.getMessage());
            }

            int i = 1;

            return null;
        }
    }
}
