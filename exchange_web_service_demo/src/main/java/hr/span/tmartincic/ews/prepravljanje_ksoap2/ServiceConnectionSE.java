//package hr.span.tmartincic.ews.prepravljanje_ksoap2;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.Proxy;
//import java.net.URL;
//import java.security.KeyStore;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.http.HttpVersion;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.NTCredentials;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.params.AuthPolicy;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.params.HttpProtocolParams;
//import org.apache.http.protocol.HTTP;
//import org.ksoap2.HeaderProperty;
//import org.ksoap2.transport.ServiceConnection;
//
//import hr.span.tmartincic.ews.MySSLSocketFactory;
//import hr.span.tmartincic.ews.ServiceInfo;
//import hr.span.tmartincic.ews.ntlmauth.NTLMSchemeFactory;
//
//public class ServiceConnectionSE
//{
////    private HttpURLConnection connection;
//    private HttpClient client;
//    private HttpPost httpPost;
//
//    public HttpClient getClient()
//    {
//        return this.client;
//    }
//
//    public HttpPost getRequest()
//    {
//        return this.httpPost;
//    }
//
//    public ServiceConnectionSE(String url)
//            throws IOException
//    {
//        this(url, 20000);
//    }
//
//    public ServiceConnectionSE(String url, int timeout)
//            throws IOException
//    {
//        this.client = instantiateClient(timeout);
//        this.httpPost = new HttpPost(ServiceInfo.serviceUrl);
//    }
//
//}