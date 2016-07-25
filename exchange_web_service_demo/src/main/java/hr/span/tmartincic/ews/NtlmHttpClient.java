package hr.span.tmartincic.ews;

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

import hr.span.tmartincic.ews.ntlmauth.*;

public class NtlmHttpClient
{
    public void doStuff(SOAPRequest soapRequest)
    {
        DefaultHttpClient httpclient = getNewHttpClient();
        // register ntlm auth scheme
        httpclient.getAuthSchemes().register(AuthPolicy.NTLM, new NTLMSchemeFactory());
        httpclient.getCredentialsProvider().setCredentials(
                // Limit the credentials only to the specified domain and port
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                // Specify credentials, most of the time only user/pass is needed
                new NTCredentials(ServiceInfo.username, ServiceInfo.password, "", "")
        );

        HttpResponse response = null;
        String responseBody = "response failed";

        try
        {
            HttpPost post = new HttpPost(ServiceInfo.serviceUrl);
            StringEntity se = new StringEntity(soapRequest.request, HTTP.UTF_8);
            se.setContentType("text/xml");
            post.setEntity(se);
//            post.addHeader("Content-Length", "8192");
            response = httpclient.execute(post);
            responseBody = EntityUtils.toString(response.getEntity());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Log.i("taggg", "responseBody =>>>>>>>>>>" + responseBody);

    }


//    public String getSoap(){
//
//        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:m=\"http://schemas.microsoft.com/exchange/services/2006/messages\" xmlns:t=\"http://schemas.microsoft.com/exchange/services/2006/types\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                "  <soap:Header>\n" +
//                "    <t:RequestServerVersion Version=\"Exchange2013_SP1\" />\n" +
//                "  </soap:Header>\n" +
//                "  <soap:Body>\n" +
//                "    <m:FindItem Traversal=\"Shallow\">\n" +
//                "      <m:ItemShape>\n" +
//                "        <t:BaseShape>AllProperties</t:BaseShape>\n" +
//                "      </m:ItemShape>\n" +
//                "      <m:CalendarView StartDate=\"2014-12-31T23:00:00.000Z\" EndDate=\"2015-06-15T13:47:48.905Z\" />\n" +
//                "      <m:ParentFolderIds>\n" +
//                "        <t:DistinguishedFolderId Id=\"calendar\" />\n" +
//                "      </m:ParentFolderIds>\n" +
//                "    </m:FindItem>\n" +
//                "  </soap:Body>\n" +
//                "</soap:Envelope>\n";
//        return s;
//    }

    public DefaultHttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}
