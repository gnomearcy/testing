package hr.span.tmartincic.implementation;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class ParseComServerAuthenticate implements ServerAuthenticate
{
    private static final String applicationId = "E4OTdSI53iMYQ7ENAsJ2CTpNxopqZ1AilgHeo1Ez";
    private static final String RESTAPIKey = "bnRURfMM5oNJWsK6KzGB0gIyIIvjbgI4u4eF32Hz";

    private static final int readTimeout = 20000;
    private static final int writeTimeout = 20000;

    @Override
    public String userSignUp(String name, String email, String pass, String authType) throws Exception
    {
        //set the endpoint
        String endPoint = "https://api.parse.com/1/users";

        URL url = new URL(endPoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        /** POST method */
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(writeTimeout);
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("X-Parse-Application-Id", applicationId);
        connection.addRequestProperty("X-Parse-REST-API-Key", RESTAPIKey);

        //add entity to HttpURLConnection
        String body = "{\"username\":\"" + email + "\",\"password\":\""
                        + pass + "\",\"phone\":\"415-392-0202\"}";
        OutputStream os = connection.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(body);
        bw.flush();
        bw.close();
        os.close();

        String authToken = null;
        StringBuilder resultBuilder = new StringBuilder();

        try
        {
            connection.connect();

            if(connection.getResponseCode() != 201)
            {
                throw new Exception("No content");
            }

            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                resultBuilder.append(line);
            }

            User retrievedUser = new Gson().fromJson(resultBuilder.toString(), User.class);
            authToken = retrievedUser.sessionToken;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            connection.disconnect();
        }

        return authToken;
    }

    @Override
    public String userSignIn(String user, String pass, String authType) throws Exception
    {
        String endPoint = "https://api.parse.com/1/login";

        String query = null;

        try{
            query = String.format("%s=%s&%s=%s", "username", URLEncoder.encode(user, "UTF-8"), "password", pass);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        endPoint += "?" + query;
        URL url = new URL(endPoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(writeTimeout);
        connection.setReadTimeout(readTimeout);
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("X-Parse-REST-API-Key", RESTAPIKey);
        connection.addRequestProperty("X-Parse-Application-Id", applicationId);

        String authToken = null;
        StringBuilder resultBuilder = new StringBuilder();

        try
        {
            connection.connect();

            if(connection.getResponseCode() != 200)
            {
                throw new Exception("No content");
            }

            InputStream in = connection.getInputStream();
            BufferedReader isr = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = isr.readLine()) != null) {
                resultBuilder.append(line);
            }

            User retrievedUser = new Gson().fromJson(resultBuilder.toString(), User.class);
            authToken = retrievedUser.sessionToken;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            connection.disconnect();
        }

        return authToken;
    }

    private class User implements Serializable {

        private String firstName;
        private String lastName;
        private String username;
        private String phone;
        private String objectId;
        public String sessionToken;
        private String gravatarId;
        private String avatarUrl;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
        }

        public String getGravatarId() {
            return gravatarId;
        }

        public void setGravatarId(String gravatarId) {
            this.gravatarId = gravatarId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
