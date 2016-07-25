package hr.span.tmartincic.ews;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;


public class SOAP extends Activity {

    private static final String tag = "ksoap2";
    private static final String SOAP_ACTION = "http://footballpool.dataaccess.eu/TopGoalScorers";
    private static final String METHOD_NAME = "TopGoalScorers";
    private static final String NAMESPACE = "http://footballpool.dataaccess.eu"; //host in POST request
    private static final String URL = "http://footballpool.dataaccess.eu/data/info.wso?WSDL";


    private TextView tv;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);

        tv= (TextView)findViewById(R.id.txt2);

        myAsyncTask myRequest = new myAsyncTask();
        myRequest.execute();

    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tv.setText(response);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            SoapObject request = new SoapObject(w3cNAMESPACE, w3cMETHOD_NAME);
            request.addProperty("iTopN", "5");
//            request.addProperty("Fahrenheit", temp);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
//            HttpTransportSE httpTransport = new HttpTransportSE(MAIN_REQUEST_URL);

            httpTransport.debug = true; //allow requestDump to be initialized with body of request.

            try {
                httpTransport.call(SOAP_ACTION, envelope);
//                httpTransport.call(soapAction, envelope);
                Log.d(tag, "Body: " + httpTransport.requestDump);
            } catch (HttpResponseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //send request
            SoapObject response = null;
            try
            {
//                response = (SoapObject)envelope.getResponse();
                response = (SoapObject) envelope.bodyIn;
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

//            Log.d("App",""+response.getProperty(1).toString());
            Log.d(tag, "Response - " + response.toString());
//            response = response.getProperty(1).toString();
            Log.d(tag, "Inner text - " + response.getInnerText());
            Log.d(tag, "Property count - " + response.getPropertyCount());

            SoapObject firstProperty = (SoapObject) response.getProperty(0);
            Log.d(tag, "First property - " + firstProperty.toString());
            Log.d(tag, "First property - " + firstProperty.getPropertyCount()); //5?
            SoapObject topGoalScorer = (SoapObject) firstProperty.getProperty(2); //messi
            Log.d(tag, "topGoalScorer - " + topGoalScorer.toString());

            return null;
        }
    }
}