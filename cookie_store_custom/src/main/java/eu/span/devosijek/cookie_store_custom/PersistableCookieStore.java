package eu.span.devosijek.cookie_store_custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.NullPointerException;import java.lang.String;import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *  Implementation of java.net.CookieStoreImpl
 *  CookieStoreImpl is a final class, therefor it can't be extended.
 *  CookieStoreImpls' code is extended with persistence through
 *  SharedPreference local storage.
 */
public class PersistableCookieStore implements CookieStore
{
    private static final String tag = "PersistentCookieStore";

    /** this map may have null keys! */
    private final Map<URI, List<HttpCookie>> map = new HashMap<URI, List<HttpCookie>>();
    private SharedPreferences sharedPreferences;

    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String COOKIE_LOCAL_STORE = "CookieLocalStore";

    public PersistableCookieStore(Context context)
    {
        this.sharedPreferences = context.getSharedPreferences(COOKIE_PREFS, 0);
    }

    public synchronized void add(URI uri, HttpCookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie == null");
        }

        uri = cookiesUri(uri);
        List<HttpCookie> cookies = map.get(uri);
        if (cookies == null) {
            cookies = new ArrayList<HttpCookie>();
            map.put(uri, cookies);
        } else {
            cookies.remove(cookie);
        }
        cookies.add(cookie);

        //todo
        saveLocally();
    }

    private void saveLocally()
    {
        SharedPreferences.Editor prefsWriter = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonified = gson.toJson(map);
        prefsWriter.putString(COOKIE_LOCAL_STORE, jsonified);
        prefsWriter.commit();
    }

    private Map<URI, List<HttpCookie>> readLocally()
    {
        String jsonified = sharedPreferences.getString(COOKIE_LOCAL_STORE, null);
        if(jsonified == null)
        {
            return null;
        }

        Gson gson = new Gson();
        TypeToken token = new TypeToken<HashMap<URI, List<HttpCookie>>>(){};

        Map<URI, List<HttpCookie>> result;
        try
        {
            result = gson.fromJson(jsonified, token.getType());
            return result;
        }
        catch(JsonSyntaxException exception)
        {
            Log.d(tag, "Method \"readLocally\" - JsonSyntaxException");
            exception.printStackTrace();
        }
        catch(JsonParseException exception)
        {
            Log.d(tag, "Method \"readLocally\" - JsonParseException");
            exception.printStackTrace();
        }

        return null;
    }

    private URI cookiesUri(URI uri) {
        if (uri == null) {
            return null;
        }
        try {
            return new URI("http", uri.getHost(), null, null);
        } catch (URISyntaxException e) {
            return uri; // probably a URI with no host
        }
    }

    public synchronized List<HttpCookie> get(URI uri) {
        if (uri == null) {
            throw new NullPointerException("uri == null");
        }

        boolean removedAny = false;

        List<HttpCookie> result = new ArrayList<HttpCookie>();

        // get cookies associated with given URI. If none, returns an empty list
        List<HttpCookie> cookiesForUri = map.get(uri);
        if (cookiesForUri != null) {
            for (Iterator<HttpCookie> i = cookiesForUri.iterator(); i.hasNext(); ) {
                HttpCookie cookie = i.next();
                if (cookie.hasExpired())
                {
                    i.remove(); // remove expired cookies
                    removedAny = true; //todo
                } else {
                    result.add(cookie);
                }
            }
        }



        // get all cookies that domain matches the URI
        for (Map.Entry<URI, List<HttpCookie>> entry : map.entrySet()) {
            if (uri.equals(entry.getKey())) {
                continue; // skip the given URI; we've already handled it
            }

            List<HttpCookie> entryCookies = entry.getValue();
            for (Iterator<HttpCookie> i = entryCookies.iterator(); i.hasNext(); ) {
                HttpCookie cookie = i.next();
                if (!HttpCookie.domainMatches(cookie.getDomain(), uri.getHost())) {
                    continue;
                }
                if (cookie.hasExpired())
                {
                    i.remove(); // remove expired cookies
                    removedAny = true; //todo
                } else if (!result.contains(cookie)) {
                    result.add(cookie);
                }
            }
        }

        if(removedAny)
        {
            saveLocally(); //todo
        }

        return Collections.unmodifiableList(result);
    }

    public synchronized List<HttpCookie> getCookies() {

        boolean removedAny = false;
        Log.d(tag, "Method \"getCookies\"");

        //check if we have something saved in local storage
        //todo add test to check in local storage for map of cookies
//        readLocally();

        List<HttpCookie> result = new ArrayList<HttpCookie>();
        for (List<HttpCookie> list : map.values()) {
            for (Iterator<HttpCookie> i = list.iterator(); i.hasNext(); ) {
                HttpCookie cookie = i.next();
                if (cookie.hasExpired())
                {
                    i.remove(); // remove expired cookies
                    removedAny = true; //todo
                }
                else if (!result.contains(cookie))
                {
                    result.add(cookie);
                }
            }
        }
        //todo
        if(removedAny)
        {
            saveLocally();
        }

        return Collections.unmodifiableList(result);
    }

    public synchronized List<URI> getURIs() {
        List<URI> result = new ArrayList<URI>(map.keySet());
        result.remove(null); // sigh
        return Collections.unmodifiableList(result);
    }

    public synchronized boolean remove(URI uri, HttpCookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie == null");
        }

        boolean removedAny;

        List<HttpCookie> cookies = map.get(cookiesUri(uri));
        if (cookies != null)
        {
            removedAny = cookies.remove(cookie);
            if(removedAny)
            {
                saveLocally(); //todo
                return true;
            }
            return false;
        }
        else
        {
            return false;
        }
    }

    public synchronized boolean removeAll() {
        boolean result = !map.isEmpty();
        map.clear();
        saveLocally(); //todo
        return result;
    }
}
