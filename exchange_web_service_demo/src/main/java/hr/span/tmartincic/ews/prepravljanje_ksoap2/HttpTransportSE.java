package hr.span.tmartincic.ews.prepravljanje_ksoap2;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.Transport;
import org.xmlpull.v1.XmlPullParserException;

import hr.span.tmartincic.ews.MySSLSocketFactory;
import hr.span.tmartincic.ews.ServiceInfo;
import hr.span.tmartincic.ews.ntlmauth.NTLMSchemeFactory;

public class HttpTransportSE
        extends Transport
{
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpResponse httpResponse;
    private int timeout = 20000;
    private String requestXML;

    private DefaultHttpClient setAuthScheme(DefaultHttpClient cl)
    {
        cl.getAuthSchemes().register(AuthPolicy.NTLM, new NTLMSchemeFactory());
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

    public HttpTransportSE(String request)
    {
        super(ServiceInfo.serviceUrl);
        this.requestXML = request;
        this.httpClient = instantiateClient();
        this.httpPost = new HttpPost(ServiceInfo.serviceUrl);
    }

    @Override
    public ServiceConnection getServiceConnection() throws IOException
    {
        return null;
    }

    public void call(String soapAction, SoapEnvelope envelope)
            throws HttpResponseException, IOException, XmlPullParserException
    {
        call(soapAction, envelope, null);
    }

    public List call(String soapAction, SoapEnvelope envelope, List headers)
            throws HttpResponseException, IOException, XmlPullParserException
    {
        if (soapAction == null) {
            soapAction = "\"\"";
        }
        byte[] requestData = createRequestData(envelope, "UTF-8");

        this.requestDump = (this.debug ? new String(requestData) : null);
        this.responseDump = null;

        httpPost.addHeader("User-Agent", "ksoap2-android/2.6.0+");

        if (envelope.version != 120) {
            httpPost.addHeader("SOAPAction", soapAction);
        }
        if (envelope.version == 120) {
            httpPost.addHeader("Content-Type", "application/soap+xml;charset=utf-8");
        } else {
            httpPost.addHeader("Content-Type", "text/xml;charset=utf-8");
        }
        httpPost.addHeader("Accept-Encoding", "gzip");

        if (headers != null) {
            for (int i = 0; i < headers.size(); i++)
            {
                HeaderProperty hp = (HeaderProperty)headers.get(i);
                httpPost.addHeader(hp.getKey(), hp.getValue());
            }
        }

        // Body
        StringEntity se = new StringEntity(this.requestXML, "UTF-8");
        this.httpPost.setEntity(se);
        this.httpResponse = httpClient.execute(httpPost);
//        InputStream iss = httpResponse.getEntity().getContent();
//        httpResponse.getEntity().getContentEncoding()

        requestData = null;
        InputStream is = null;
        Header[] retHeaders = null;
        byte[] buf = null;
        int contentLength = 8192;
        boolean gZippedContent = false;
        boolean xmlContent = false;

        //Read the code
        int status = httpResponse.getStatusLine().getStatusCode();

        try
        {
            retHeaders = httpResponse.getAllHeaders();
            for (int i = 0; i < retHeaders.length; i++)
            {
//                HeaderProperty hp = (HeaderProperty)retHeaders[i];
                Header hp = retHeaders[0];
//                Header h = retHeaders[0];

                if (null != hp.getName())
                {
                    if ((hp.getName().equalsIgnoreCase("content-length")) &&
                            (hp.getValue() != null)) {
                        try
                        {
                            contentLength = Integer.parseInt(hp.getValue());
                        }
                        catch (NumberFormatException nfe)
                        {
                            contentLength = 8192;
                        }
                    }
                    if ((hp.getName().equalsIgnoreCase("Content-Type")) && (hp.getValue().contains("xml"))) {
                        xmlContent = true;
                    }
                    if ((hp.getName().equalsIgnoreCase("Content-Encoding")) && (hp.getValue().equalsIgnoreCase("gzip"))) {
                        gZippedContent = true;
                    }
                }
            }

            if (status != 200) {
                throw new HttpResponseException("HTTP request failed, HTTP status: " + status, status);
            }
            if (contentLength > 0) {
                if (gZippedContent) {
//                    is = getUnZippedInputStream(new BufferedInputStream(connection.openInputStream(), contentLength));
                    is = getUnZippedInputStream(new BufferedInputStream(httpResponse.getEntity().getContent(), contentLength));
                } else {
//                    is = new BufferedInputStream(connection.openInputStream(), contentLength);
                    is = new BufferedInputStream(httpResponse.getEntity().getContent(), contentLength);
                }
            }
        }
        catch (IOException e)
        {
//            if (contentLength > 0) {
//                if (gZippedContent) {
////                    is = getUnZippedInputStream(new BufferedInputStream(connection.getErrorStream(), contentLength));
//                    is = getUnZippedInputStream(new BufferedInputStream(), contentLength));
//                } else {
//                    is = new BufferedInputStream(connection.getErrorStream(), contentLength);
//                }
//            }
//            if (((e instanceof HttpResponseException)) &&
//                    (!xmlContent))
//            {
//                if ((this.debug) && (is != null)) {
//                    readDebug(is, contentLength, outputFile);
//                }
//                connection.disconnect();
//                throw e;
//            }
        }
//        if (this.debug) {
//            is = readDebug(is, contentLength, outputFile);
//        }
//        parseResponse(envelope, is, retHeaders);

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        Log.d("ksoap2", sb.toString());

        parseResponse(envelope, is);

        is = null;
        buf = null;

        return Arrays.asList(retHeaders);
    }

    @Override
    public List call(String s, SoapEnvelope soapEnvelope, List list, File file) throws IOException, XmlPullParserException
    {
        return null;
    }

    protected void parseResponse(SoapEnvelope envelope, InputStream is, List returnedHeaders)
            throws XmlPullParserException, IOException
    {
        parseResponse(envelope, is);
    }

    private InputStream readDebug(InputStream is, int contentLength, File outputFile)
            throws IOException
    {
        OutputStream bos;
        if (outputFile != null) {
            bos = new FileOutputStream(outputFile);
        } else {
            bos = new ByteArrayOutputStream(contentLength > 0 ? contentLength : 262144);
        }
        byte[] buf = new byte[256];
        for (;;)
        {
            int rd = is.read(buf, 0, 256);
            if (rd == -1) {
                break;
            }
            bos.write(buf, 0, rd);
        }
        bos.flush();
        if ((bos instanceof ByteArrayOutputStream)) {
            buf = ((ByteArrayOutputStream)bos).toByteArray();
        }
//        OutputStream boss = null;
        this.responseDump = new String(buf);
        is.close();
        if (outputFile != null) {
            return new FileInputStream(outputFile);
        }
        return new ByteArrayInputStream(buf);
    }

    private InputStream getUnZippedInputStream(InputStream inputStream)
            throws IOException
    {
        try
        {
            return (GZIPInputStream)inputStream;
        }
        catch (ClassCastException e) {}
        return new GZIPInputStream(inputStream);
    }
}
