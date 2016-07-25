package hr.span.tmartincic.ews.ntlmauth;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.Transport;
import org.xmlpull.v1.XmlPullParserException;

import hr.span.tmartincic.ews.MySSLSocketFactory;
import hr.span.tmartincic.ews.ServiceInfo;

/**
 * A transport to be used with NTLM.
 *
 * Inspired by http://hc.apache.org/httpcomponents-client-ga/ntlm.html
 * @author Lian Hwang lian_hwang@hotmail.com
 * @author Manfred Moser <manfred@simpligity.com>
 */
public class NtlmTransport extends Transport
{
    private final DefaultHttpClient client = instantiateClient();
    private final HttpContext localContext = new BasicHttpContext();

    public List call(String targetNamespace, SoapEnvelope envelope, List headers)
            throws IOException, XmlPullParserException {
        return call(targetNamespace, envelope, headers, null);
    }

    public List call(String soapAction, SoapEnvelope envelope, List headers, File outputFile)
            throws IOException, XmlPullParserException {
        if (outputFile != null) {
            // implemented in HttpTransportSE if you are willing to port..
            throw new RuntimeException("Writing to file not supported");
        }
        HttpResponse resp = null;


        try {
            HttpPost httppost = new HttpPost(ServiceInfo.serviceUrl);
            setHeaders(soapAction, envelope, httppost, headers);
            resp = client.execute(httppost, localContext);
            HttpEntity respEntity = resp.getEntity();
            InputStream is = respEntity.getContent();

            //write out response dump variable
            is = readDebug(is, 200000);
            parseResponse(envelope, is);

        } catch (Exception ex) {
            Log.d("ksoap2", "Message - " + ex.getMessage());
            Log.d("ksoap2", "ex string - " + ex.toString());
            ex.printStackTrace();
        }

        if (resp != null) {
            return Arrays.asList(resp.getAllHeaders());
        } else {
            return null;
        }
    }

    private InputStream readDebug(InputStream is, int contentLength) throws IOException
    {
        Object bos = new ByteArrayOutputStream(contentLength > 0 ? contentLength : 262144);

        byte[] buf = new byte[256];

        while(true) {
            int rd = is.read(buf, 0, 256);
            if(rd == -1) {
                ((OutputStream)bos).flush();
                if(bos instanceof ByteArrayOutputStream) {
                    buf = ((ByteArrayOutputStream)bos).toByteArray();
                }

                bos = null;
                this.responseDump = new String(buf);
                is.close();
                return (InputStream)(new ByteArrayInputStream(buf));
            }

            ((OutputStream)bos).write(buf, 0, rd);
        }
    }

    private void setHeaders(String soapAction, SoapEnvelope envelope, HttpPost httppost, List headers) {
        byte[] requestData = null;
        try {
            requestData = createRequestData(envelope);
            this.requestDump = new String(requestData);

        } catch (IOException iOException) {
        }
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestData);
        httppost.setEntity(byteArrayEntity);
        httppost.addHeader("User-Agent", org.ksoap2.transport.Transport.USER_AGENT);
        // SOAPAction is not a valid header for VER12 so do not add
        // it
        // @see "http://code.google.com/p/ksoap2-android/issues/detail?id=67
        if (envelope.version != SoapSerializationEnvelope.VER12) {
            httppost.addHeader("SOAPAction", soapAction);
        }

        if (envelope.version == SoapSerializationEnvelope.VER12) {
            httppost.addHeader("Content-Type", Transport.CONTENT_TYPE_SOAP_XML_CHARSET_UTF_8);
        } else {
            httppost.addHeader("Content-Type", Transport.CONTENT_TYPE_XML_CHARSET_UTF_8);
        }

        // Pass the headers provided by the user along with the call
        if (headers != null) {
            for (int i = 0; i < headers.size(); i++) {
                HeaderProperty hp = (HeaderProperty) headers.get(i);
                httppost.addHeader(hp.getKey(), hp.getValue());
            }
        }
    }

    //NTLM Scheme factory
    private class NTLMSchemeFactory implements AuthSchemeFactory {
        public AuthScheme newInstance(final HttpParams params) {
            // see http://www.robertkuzma.com/2011/07/
            // manipulating-sharepoint-list-items-with-android-java-and-ntlm-authentication/
            return new NTLMScheme(new JCIFSEngine());
        }
    }

    public ServiceConnection getServiceConnection() throws IOException
    {
        throw new IOException("Not using ServiceConnection in transport");
    }

    private DefaultHttpClient setAuthScheme(DefaultHttpClient cl)
    {
        cl.getAuthSchemes().register(AuthPolicy.NTLM, new hr.span.tmartincic.ews.ntlmauth.NTLMSchemeFactory());
        cl.getCredentialsProvider().setCredentials(
                // Limit the credentials only to the specified domain and port
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                // Specify credentials, most of the time only user/pass is needed
                new NTCredentials(ServiceInfo.username, ServiceInfo.password, "", "")
        );

        return cl;
    }

    private DefaultHttpClient instantiateClient()
    {
        try
        {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpConnectionParams.setConnectionTimeout(params, timeout);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return setAuthScheme(new DefaultHttpClient(ccm, params));
        }
        catch(Exception e)
        {
            return setAuthScheme(new DefaultHttpClient());
        }
    }
}