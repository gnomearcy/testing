package hr.span.tmartincic.couchgcmsync;

import android.app.Application;
import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
//import android.util.Log;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GcmMessageHandler extends IntentService
{
    private static final String tag = "Couch Gcm Sync";
    private Handler handler;
    private Intent intent;

    private static final String syncurl = "https://gnomearcy:hx1dwr22@gnomearcy.cloudant.com/crud";
    private static final String db_name = "crud";

    public GcmMessageHandler()
    {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        this.intent = intent;
        showToast();

        Database database = getDatabase();
        if(database != null)
        {
            URL url = null;
            try
            {
                url = new URL(syncurl);
                Replication pull = database.createPullReplication(url);
                pull.addChangeListener(getReplicationListener());
                pull.start();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        }
    }

    private Replication.ChangeListener getReplicationListener()
    {
        return new Replication.ChangeListener()
        {
            @Override
            public void changed(Replication.ChangeEvent event)
            {
                Log.d(tag, "GCM status is " + event.getSource().getStatus());
                if(event.getSource().getStatus() == Replication.ReplicationStatus.REPLICATION_STOPPED)
                {
                    //Finish the wakeful intent that originally started this ServiceIntent (GcmMessageHandler)
                    //wakeful intent is the one that invoked the broadcast receiver (GcmBroadcastReceiver)
                    GcmBroadcastReceiver.completeWakefulIntent(intent);
                }
            }
        };
    }

    private Database getDatabase()
    {
        try
        {
            Manager manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
            return manager.getDatabase(db_name);

        }
        catch(IOException | CouchbaseLiteException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void showToast()
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getApplicationContext(), "Server ping - sync down!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
