package hr.span.tmartincic.couchgcmsync;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class GCMSyncAct extends ActionBarActivity
{
    private static final String tag = "Couch Gcm Sync";
    private static final String projectId = "4515256755";
    private static final String apiKey = "AIzaSyBhX2P7_xVOzSuKHqSLxH-XdFaMqNol0IE";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcmsync);
    }

    private void getDeviceToken()
    {
        new AsyncTask()
        {
            @Override
            protected Object doInBackground(Object[] params)
            {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                String deviceToken = null;
                try
                {
                    deviceToken = gcm.register(projectId);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                Log.i(tag, "Device token : " + deviceToken);

                //update user profile document
                return null;
            }
        }.execute(null, null, null);
    }
}
