package hr.span.tmartincic.roomassistant.web;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyStore;

import hr.span.tmartincic.roomassistant.ServiceInfo;

public class SOAPClient
{
    private static final String tag = "request";
    private static final int timeout = 20000;

    //Header names
    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String HEADER_COOKIE = "Cookie";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_HOST = "Host";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_EXPECT = "Expect";

    private OnServiceListener listener;

    public interface OnServiceListener
    {
        void onServiceSuccess(String responseXml);
    }

    public void callService(String requestXml, OnServiceListener listener)
    {
        this.listener = listener;
        ServiceExecutor serviceCall = new ServiceExecutor();
        serviceCall.execute(requestXml);
    }

    private class ServiceExecutor extends AsyncTask<String, Void, String>
    {
        DefaultHttpClient httpclient;

        public ServiceExecutor()
        {
            Log.d("ksoap2", "Initializing ServiceExecutor instance");
            httpclient = getNewHttpClient();
            httpclient.getAuthSchemes().register(AuthPolicy.NTLM, new NTLMSchemeFactory());
            httpclient.getCredentialsProvider().setCredentials(
                    // Limit the credentials only to the specified domain and port
                    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    // Specify credentials, most of the time only user/pass is needed
                    new NTCredentials(ServiceInfo.username, ServiceInfo.password, "", "")
            );
        }

        @Override
        protected String doInBackground(String... requestXmls)
        {
            Log.d("ksoap2", "Do in background - service executor");
            String responseBody = "Response failed";
            String requestXml = requestXmls[0];
            try
            {
                HttpResponse response;
//                HttpPost post = new HttpPost(ServiceInfo.serviceUrl);
                HttpPost post = new HttpPost(ServiceInfo.serviceUrl);
//                StringEntity se = new StringEntity(requestXml, HTTP.UTF_8);
                StringEntity se = new StringEntity(requestXml); //request is already encoded in XTransport
                se.setContentType("text/xml");
                post.setEntity(se);

                response = httpclient.execute(post);
                responseBody = EntityUtils.toString(response.getEntity());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            Log.d("ksoap2", "responseBody =>>>>>>>>>>" + responseBody);
            Log.d("ksoap2", responseBody);

            return responseBody;
        }

        @Override
        protected void onPostExecute(String responseXml)
        {
            listener.onServiceSuccess(responseXml);
        }

        public DefaultHttpClient getNewHttpClient() {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);

                MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//                HttpConnectionParams.setConnectionTimeout(params, timeout);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

                return new DefaultHttpClient(ccm, params);
            }
            catch (Exception e)
            {
                return new DefaultHttpClient();
            }
        }
    }
}
