package hr.span.tmartincic.implementation.implementation;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.media.tv.TvContract;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.span.tmartincic.implementation.AccountGeneral;
import hr.span.tmartincic.implementation.implementation.dbscheme.TvShowsContentProvider;
import hr.span.tmartincic.implementation.implementation.dbscheme.TvShowsContract;
import hr.span.tmartincic.implementation.implementation.models.TvShow;
import static java.lang.System.currentTimeMillis;

public class MainActivity extends ActionBarActivity
{
    private static final String tag = "SyncAdapter";
    private AccountManager accountManager;
    private Account connectedAccount;           //current account
    private String authToken = null;
    private Object handleSyncObserver;

    /*
    * listener with a callback to let me know what is my sync adapter up to.
    * */
    private SyncStatusObserver sso = new SyncStatusObserver()
    {
        @Override
        public void onStatusChanged(int i)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    refreshSyncStatus();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inject context into AccountManager
        accountManager = AccountManager.get(this);

        findViewById(R.id.btnShowRemoteList).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new AsyncTask<Void, Void, List<TvShow>>()
                {
                    ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

                    @Override
                    protected void onPreExecute()
                    {
                        if (authToken == null) {
                            Toast.makeText(MainActivity.this, "Please connect first", Toast.LENGTH_SHORT).show();
                            cancel(true);
                        } else {
                            progressDialog.show();
                        }
                    }

                    @Override
                    protected List<TvShow> doInBackground(Void... voids)
                    {
                        ParseComServerAccessor pcsa = new ParseComServerAccessor();
                        try
                        {
                            return pcsa.getShows(authToken);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(List<TvShow> tvShows)
                    {
                        progressDialog.dismiss();
                        if(tvShows != null)
                        {
                            showOnDialog("Remote Tv Shows", tvShows);
                        }
                    }
                }.execute();

            }
        });

        findViewById(R.id.btnShowLocalList).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                List<TvShow> shows = readFromContentProvider();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Local Tv shows");
                builder.setAdapter(new ArrayAdapter<TvShow>(MainActivity.this, android.R.layout.simple_list_item_1, shows), null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        findViewById(R.id.btnClearLocalData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deleteing all the TV Shows on the DB. This normally should be done on a background thread
                int numDeleted = getContentResolver().delete(TvShowsContract.CONTENT_URI, null, null);
                Toast.makeText(MainActivity.this, "Deleted " + numDeleted + " TV shows from the local list", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnAddShow).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String tvshowsNames[] = getResources().getStringArray(R.array.tvshows_names);
                int tvshowsYears[] = getResources().getIntArray(R.array.tvshows_year);
                int randIdx = new Random(currentTimeMillis()).nextInt(tvshowsNames.length);

                // Creating a Tv Show data object, in order to use some of its convenient methods
                TvShow tvShow = new TvShow(tvshowsNames[randIdx], tvshowsYears[randIdx]);
                Log.d("udinic", "Tv Show to add [id="+randIdx+"]: " + tvShow.toString());

                // Add our Tv show to the local data base. This normally should be done on a background thread
                getContentResolver().insert(TvShowsContract.CONTENT_URI, tvShow.getContentValues());

                Toast.makeText(MainActivity.this, "Added \"" + tvShow.toString() + "\"", Toast.LENGTH_SHORT).show();
            }
        });

        //Account Stuff

        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });
    }

    private List<TvShow> readFromContentProvider()
    {
        Cursor showsCursor = getContentResolver().query(TvShowsContract.CONTENT_URI, null, null, null, null);
        List<TvShow> shows = new ArrayList<TvShow>();

        if(showsCursor != null)
        {
            while(showsCursor.moveToNext())
            {
                shows.add(TvShow.fromCursor(showsCursor));
            }
            showsCursor.close();
        }
        return shows;
    }

    private void showOnDialog(String title, List<TvShow> tvShows)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setAdapter(new ArrayAdapter<TvShow>(MainActivity.this, android.R.layout.simple_list_item_1, tvShows), null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        }).show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        handleSyncObserver = getContentResolver().addStatusChangeListener(ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING, sso);
    }

    @Override
    protected void onPause()
    {
        if(handleSyncObserver != null)
            getContentResolver().removeStatusChangeListener(handleSyncObserver);
        super.onPause();
    }

    private void refreshSyncStatus()
    {
        String status;

        if (ContentResolver.isSyncActive(connectedAccount, TvShowsContract.AUTHORITY))
            status = "Status: Syncing..";
        else if (ContentResolver.isSyncPending(connectedAccount, TvShowsContract.AUTHORITY))
            status = "Status: Pending..";
        else
            status = "Status: Idle";

        ((TextView) findViewById(R.id.status)).setText(status);
        Log.d("udinic", "refreshSyncStatus> " + status);
    }

    private void initButtonsAfterConnect()
    {
        String authority = TvShowsContract.AUTHORITY;

        //THIS IS THE "ENABLED" FLAG OF THE SYNC ADAPTER
        //checks if the account/provider is syncable
        //>0 if it is syncable, 0 if not, and <0 if the state isn't known yet.
        int isSyncable = ContentResolver.getIsSyncable(connectedAccount, authority);

        boolean autoSync = ContentResolver.getSyncAutomatically(connectedAccount, authority);

        ((CheckBox)findViewById(R.id.cbIsSyncable)).setChecked(isSyncable > 0);
        ((CheckBox)findViewById(R.id.cbAutoSync)).setChecked(autoSync);

        findViewById(R.id.cbIsSyncable).setEnabled(true);
        findViewById(R.id.cbAutoSync).setEnabled(true);
        findViewById(R.id.status).setEnabled(true);
        findViewById(R.id.btnShowRemoteList).setEnabled(true);
        findViewById(R.id.btnSync).setEnabled(true);
        findViewById(R.id.btnConnect).setEnabled(false);

        refreshSyncStatus();
    }

    private void showMessage(final String msg)
    {
        if (msg == null || msg.trim().equals(""))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        /*
        * getAuthTokenByFeatures - accountManager zove authenticator koji zove custom backend za autentikaciju
        * authenticator sprema response u obliku bundle-a i vraca ga, accountManager ga sprema pod svojim public konstantama kao sto su
        * KEY_ACCOUNT_NAME, KEY_AUTHTOKEN itd.
        * */
        final AccountManagerFuture<Bundle> future = accountManager.getAuthTokenByFeatures(accountType, authTokenType, null, MainActivity.this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> accountManagerFuture)
                    {
                        try
                        {
                            Bundle resultBundle = accountManagerFuture.getResult();
                            authToken = resultBundle.getString(AccountManager.KEY_AUTHTOKEN);

                            //if the retrieved auth token is valid
                            if(authToken != null)
                            {
                                String accountName = resultBundle.getString(AccountManager.KEY_ACCOUNT_NAME);
                                connectedAccount = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
                                initButtonsAfterConnect();
                            }

                            showMessage(((authToken != null) ? "SUCCESS!\ntoken: " + authToken : "FAIL"));
                            Log.d("udinic", "GetTokenForAccount Bundle is " + resultBundle);
                        }
                        catch (OperationCanceledException | AuthenticatorException | IOException e)
                        {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }, null);
    }
}
