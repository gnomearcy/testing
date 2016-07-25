package hr.span.tmartincic.couchdb;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Status;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.internal.Body;
import com.couchbase.lite.internal.RevisionInternal;
import com.couchbase.lite.replicator.Replication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Dashboard:
 * https://gnomearcy.cloudant.com/dashboard.html
 */
public class CouchActivity extends ActionBarActivity
{
    private static final String tag = "Couch_database";
    private static final String db_name = "crud";
    private static final String welcome_id = "welcome";
//    private static final String document_summerId = "summerId";
    private static final String syncurl = "https://gnomearcy:hx1dwr22@gnomearcy.cloudant.com/crud"; //http://<username>.cloudant.com/<name_of_db>
    private static final String synurl_welcome = syncurl + welcome_id;
//    private static final String url_summer = "https://gnomearcy.cloudant.com/crud/" + document_summerId;

    Database database;
    Database.ChangeListener databaseListener;
    Replication.ChangeListener pullReplicationListener;
    Replication.ChangeListener pushReplicationListener;

    private void initListeners()
    {
        Log.d(tag, "Init listeners.");

        databaseListener = new Database.ChangeListener()
        {
            @Override
            public void changed(Database.ChangeEvent event)
            {
//                Log.d(tag, "DatabaseListener");
            }
        };

        pullReplicationListener = new Replication.ChangeListener()
        {
            @Override
            public void changed(Replication.ChangeEvent event)
            {
//                Log.d(tag, "ReplicationListener - PULL");
            }
        };

        pushReplicationListener = new Replication.ChangeListener()
        {
            @Override
            public void changed(Replication.ChangeEvent event)
            {
//                Log.d(tag, "ReplicationListener - PUSH");
            }
        };
    }

    protected void initDB() throws IOException, CouchbaseLiteException
    {
        Manager manager = new Manager(new AndroidContext(CouchActivity.this), Manager.DEFAULT_OPTIONS);

        //get or create the database
        database = manager.getDatabase(db_name);

        //change listener
        database.addChangeListener(databaseListener);
    }

    //start a bi-directional sync
    protected void startSync()
    {
        URL syncURL;
        try
        {
            syncURL = new URL(syncurl);
        }
        catch(MalformedURLException e)
        {
            Log.d(tag, "startSync - exception thrown");
            throw new RuntimeException();
        }

        Log.d(tag, "startSync - no exception - continuing");
        //server - client
        Replication pullReplication = database.createPullReplication(syncURL);
        pullReplication.setContinuous(true);

        //client - server
        Replication pushReplication = database.createPushReplication(syncURL);
        pushReplication.setContinuous(true);

        //replication listeners
        pullReplication.addChangeListener(pullReplicationListener);
        pushReplication.addChangeListener(pushReplicationListener);

        //start both replications
        pullReplication.start();
        pushReplication.start();
    }

    private void readDocument(String documentId)
    {
//        Document doc = database.getDocument(document_summerId);
        Document doc = database.getExistingDocument(documentId);

        if(doc == null)
        {
            Log.d(tag, "Document is null");
        }
        else
        {
            Log.d(tag, "Document is not null");
            Map<String, Object> properties = null;

            try
            {
                Log.d(tag, "Doc to string-> " + doc.toString());

                properties = doc.getProperties();
//                doc.getProperty("season");
            }
            catch(Exception e)
            {
                Log.d(tag, "GetProperties exception - " + e.getMessage());
            }

            if(properties == null)
            {
                Log.d(tag, "properties are null");
            }
            else
            {
                Log.d(tag, "proprties are not null");
                Set<String> keys = properties.keySet();

                for(String key : keys)
                {
                    Log.d(tag, "key - " + key);
                }
            }
        }
    }

    private void insertDocument(String documentId) throws CouchbaseLiteException
    {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("title", "Little, Big");
        properties.put("author", "John Crowley");
        properties.put("published", 1982);
        Document document = database.getDocument(documentId);
        try {
            document.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            Log.e(tag, "Cannot save document- it probably already exists.", e);
        }
    }

    private void updateDocument(String documentId) throws CouchbaseLiteException
    {
        Status status = new Status();

        HashMap<String, Object> newProperties = new HashMap<String, Object>();
        newProperties.put("_id", "customId");
        newProperties.put("_rev", "7-5b9723de31ff6d5e1a133402915bb4fd");
        newProperties.put("author", "John Crowley");
        newProperties.put("title", "Little, Big");
        newProperties.put("published", 111);

        /** Body is either a request / response or a document body. */
        Body body = new Body(newProperties);
        RevisionInternal revisionInternal = new RevisionInternal(body, database);
        database.putRevision(revisionInternal, "novirevision", true, status);

        Log.d(tag, "Status -> " + status.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couch);

        try{
            initListeners();
            initDB();
            startSync();
        }
        catch (CouchbaseLiteException | IOException e)
        {
            Log.d(tag, "onCreate - exception occured");
            e.printStackTrace();
        }

//        try
//        {
//            insertDocument("customId");
//        }
//        catch (CouchbaseLiteException e)
//        {
//            Log.d(tag, "failed to insertDocument to database");
//            e.printStackTrace();
//        }
////
////        readDocument("customId");
//        readDocument("welcome");

        try
        {
            updateDocument("customId");
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
        }
    }
}
