package eu.span.devosijek.cookie_store_custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SerializableCookie;

import org.apache.http.cookie.Cookie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Character;import java.lang.ClassNotFoundException;import java.lang.Integer;import java.lang.Override;import java.lang.String;import java.lang.StringBuilder;import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  Implementation of java.net.CookieStoreImpl
 *  with addition of persisting cookies through SharedPreferences.
 */
public class MyCookieStore implements CookieStore
{
    private static final String tag = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private final ConcurrentHashMap<String, HttpCookie> cookies;
    private final SharedPreferences cookiePrefs;

    public MyCookieStore(Context context)
    {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new ConcurrentHashMap<>();

        //Load any previously stored cookies into the store

        String storedCookieNames = cookiePrefs.getString(COOKIE_NAME_STORE, null);
        Log.d(tag, "Stored cookie named -> " + storedCookieNames);
        if(storedCookieNames != null)
        {
            String[] cookieNames = TextUtils.split(storedCookieNames, ",");
            for(String cookieName : cookieNames)
            {
                String encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX + cookieName, null);
                if(encodedCookie != null)
                {
                    HttpCookie decodedCookie = decodeCookie(encodedCookie);
                    cookies.put(cookieName, decodedCookie);
                }
            }

            clearExpired();
        }
    }

    private boolean clearExpired()
    {
        boolean clearedAny = false;
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();

        for(ConcurrentHashMap.Entry<String, HttpCookie> entry : cookies.entrySet())
        {
            String name = entry.getKey();
            HttpCookie cookie = entry.getValue();
            Log.d(tag, "Method \"clearExpired\" - cookie name value: " + name + "/" + cookie.toString());
            if(cookie.hasExpired())
            {
                cookies.remove(name);
                prefsWriter.remove(COOKIE_NAME_PREFIX + name);
                clearedAny = true;
            }
        }

        if(clearedAny)
        {
            prefsWriter.putString(COOKIE_NAME_STORE, TextUtils.join(",", cookies.keySet()));
        }
        prefsWriter.commit();

        return clearedAny;
    }

    /**
     * Returns cookie decoded from cookie string
     *
     * @param cookieString string of cookie as returned from http request
     * @return decoded cookie or null if exception occured
     */
    private HttpCookie decodeCookie(String cookieString)
    {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        HttpCookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableHttpCookie) objectInputStream.readObject()).getCookie();
        } catch (IOException e) {
            Log.d(tag, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            AsyncHttpClient.log.d(tag, "ClassNotFoundException in decodeCookie", e);
        }

        return cookie;
    }

    private String encodeCookie(SerializableHttpCookie cookie)
    {
        if(cookie == null)
        {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            ObjectOutputStream outputStream = new ObjectOutputStream(baos);
            outputStream.writeObject(cookie);
        }
        catch(IOException e)
        {
            Log.d(tag, "Method \"encodeCookie\" - error");
            e.printStackTrace();
            return null;
        }

        return byteArrayToHexString(baos.toByteArray());
    }

    private String byteArrayToHexString(byte[] byteArray)
    {
        StringBuilder sb = new StringBuilder(byteArray.length * 2);
        for (byte element : byteArray) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
//        return sb.toString().toUpperCase(Locale.US);
        return sb.toString().toUpperCase();

    }

    /**
     * Converts hex values from strings to byte arra
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     *  Adds new cookie to local storage.
     *
     *  Uri parameter is ignored.
     *  Cookie is locally mapped based on its name and domain.
     *  Domain mapping is important for usage of the same cookie on
     *  a set of URIs with same root domain.
     */
    @Override
    public void add(URI uri, HttpCookie cookie)
    {
        String name = cookie.getName() + cookie.getDomain();
        if(cookie.hasExpired())
        {
            cookies.remove(name);
        }
        else
        {
            cookies.put(name, cookie);
        }

        //Save the cookie into persistent storage
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.putString(COOKIE_NAME_STORE, TextUtils.join(",", cookies.keySet()));
        prefsWriter.putString(COOKIE_NAME_PREFIX + name, encodeCookie(new SerializableHttpCookie(cookie)));
        prefsWriter.commit();
    }

    @Override
    public List<HttpCookie> get(URI uri)
    {
        return null;
    }

    @Override
    public List<HttpCookie> getCookies()
    {
        return null;
    }

    @Override
    public List<URI> getURIs()
    {
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie)
    {
        return false;
    }

    @Override
    public boolean removeAll()
    {
        return false;
    }
}
