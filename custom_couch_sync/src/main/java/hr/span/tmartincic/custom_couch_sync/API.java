package hr.span.tmartincic.custom_couch_sync;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class API
{
    private static final String allProductsUrl = "https://dev.span.eu/APITest/api/Products/GetAllProducts";
    private static final String textUrl = "https://dev.span.eu/APITest/api/Login/GetText";
    private static Requester requester;
    private static final String tag = "CustomSyncTag";
    private static final int timeout = 5000;
    private static OnResult listener;

    public static void getAllProducts(OnResult<ArrayList<Product>> list)
    {
//        listener = list;
//        requester = new Requester<ArrayList<Product>>();
//        requester.execute(allProductsUrl);
    }

    public static void getProduct(int id, OnResult<Product> listener)
    {

    }

    public static void getText(OnResult<Text> list)
    {
        listener = list;
        requester = new Requester();
        requester.execute(textUrl);
    }

    public static void getSession(String username)
    {

    }

    private static class Requester extends AsyncTask<String, Void, Text>
    {
        @Override
        protected Text doInBackground(String... params)
        {
            HttpURLConnection urlConnection = null;
            StringBuilder builder = new StringBuilder();

            try
            {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                Log.d(tag,  "url: " + params[0]);

                urlConnection.setRequestProperty("Content-Type", "application/json ");
                urlConnection.setConnectTimeout(timeout);
                urlConnection.setReadTimeout(timeout);

//                if (reqestType.equalsIgnoreCase("POST") || reqestType.equalsIgnoreCase("PUT")){
//                    urlConnection.setDoOutput(true);
//                    OutputStream out = urlConnection.getOutputStream();
//                    OutputStreamWriter outputWriter = new OutputStreamWriter(out);
//                    // GSON WAS HERE
//                    outputWriter.write(gson.toJson(outObject));
//                    outputWriter.close();
//                }

                int	responseCode = urlConnection.getResponseCode();

                Log.d(tag, "Response code - " + String.valueOf(responseCode));

                if (responseCode != HttpURLConnection.HTTP_OK){
                    String responseMessage = urlConnection.getResponseMessage();
                    InputStream errorIn = urlConnection.getErrorStream();
                    StringBuilder errorBuilder = new StringBuilder();
                    if (errorIn != null){
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(errorIn));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            errorBuilder.append(line);
                        }
                    }
                    throw new Exception("HTTP response code: " +  responseCode + "; Message: " + responseMessage + " Error stream: " + errorBuilder.toString());
                }

                InputStream in = urlConnection.getInputStream();

                if (in != null){
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                }

                Log.d(tag, "----- OUTPUT -----");
                Log.d(tag, builder.toString());
            }
            catch (MalformedURLException e)
            {
                Log.d(tag, "MalformedURLException occured - " + e.getMessage());
                e.printStackTrace();
            }
            catch (IOException e)
            {
                Log.d(tag, "IOException occured - " + e.getMessage());

                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                urlConnection.disconnect();
            }

            Gson gson = new Gson();
            Type collectionType = new TypeToken<Text>(){}.getType();
            Text guides = gson.fromJson(builder.toString(), collectionType);

            return guides;
        }

        @Override
        protected void onPostExecute(Text result)
        {
            listener.onResult(result);
        }
    }
}
