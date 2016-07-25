package hr.span.tmartincic.implementation.implementation;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.implementation.implementation.models.TvShow;

//encapsulates all actions to Parse.com remote server
public class ParseComServerAccessor
{
    private static final String tag = "SyncAdapter";
    private static final String _url = "https://api.parse.com/1/classes/tvshows";
    private static final String HEADER_X_AppId = "X-Parse-Application-Id";
    private static final String HEADER_X_RESTAPI = "X-Parse-REST-API-Key";
    private static final String HEADER_X_Session = "X-Parse-Session-Token";
    private static final String HEADER_ContentType = "Content-Type";
    private static final String AppId = "";
    private static final String RESTAPI = "";
    private static final String appJson = "application/json";

    public List<TvShow> getShows(String auth) throws Exception
    {
        Log.d(tag, this.getClass().getSimpleName() + " - getShows for auth - " + auth);

        URL url = new URL(_url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty(HEADER_X_AppId, AppId);
        urlConnection.addRequestProperty(HEADER_X_RESTAPI, RESTAPI);
        urlConnection.addRequestProperty(HEADER_X_Session, auth);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        TvShows shows = new TvShows();

        try{
            urlConnection.connect();

            if(urlConnection.getResponseCode() != 200) //http Ok
            {
                Log.d(tag, this.getClass().getSimpleName() + " - getShows - error occured");
                throw new Exception("Error");
            }

            StringBuilder resultBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line = br.readLine()) != null)
            {
                resultBuilder.append(line);
            }

            shows = new Gson().fromJson(resultBuilder.toString(), TvShows.class);
            return shows.shows;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally
        {
            urlConnection.disconnect();
        }

        //empty list on failure
        return shows.shows;
    }

    public void putShow(String authToken, String userId, TvShow showToAdd) throws Exception
    {
        Log.d(tag, this.getClass().getSimpleName() + " - putShow for userId - " + userId);

        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.addRequestProperty(HEADER_X_AppId, AppId);
        connection.addRequestProperty(HEADER_X_RESTAPI, RESTAPI);
        connection.addRequestProperty(HEADER_X_Session, authToken);
        connection.addRequestProperty(HEADER_ContentType, appJson);

        //TODO obrisi
        //acl JSON?
        JSONObject tvShow = new JSONObject();
        tvShow.put("name", showToAdd.name);
        tvShow.put("year", showToAdd.year);

        JSONObject acl = new JSONObject();
        JSONObject aclEveryone = new JSONObject();
        JSONObject aclMe = new JSONObject();
        aclMe.put("read", true);
        aclMe.put("write", true);
        acl.put(userId, aclMe);
        acl.put("*", aclEveryone);
        tvShow.put("ACL", acl);

        String request = tvShow.toString();
        Log.d(tag, this.getClass().getSimpleName() + " - putShow - request - " + request);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        bw.write(request);
        bw.flush();
        bw.close();

        try
        {
            connection.connect();

            if(connection.getResponseCode() != 201)
            {
                throw new Exception("No content");
            }
            else
            {
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }

                Log.d(tag, this.getClass().getSimpleName() + " - putShow - success [" + builder.toString() + "]");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            connection.disconnect();
        }
    }

    private class TvShows
    {
        public List<TvShow> shows = new ArrayList<TvShow>();
    }
}
