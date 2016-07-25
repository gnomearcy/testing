package hr.span.tmartincic.implementation.implementation;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.implementation.AccountGeneral;
import hr.span.tmartincic.implementation.implementation.dbscheme.TvShowsContract;
import hr.span.tmartincic.implementation.implementation.models.TvShow;

public class MySyncAdapter extends AbstractThreadedSyncAdapter
{
    private static final String tag = "SyncAdapter";
    private AccountManager accountManager;

    public MySyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        accountManager = AccountManager.get(context);
    }

    /*
    *   extras - contain ContentResolver.SYNC_EXTRAS_UPLOAD flag when the changes happened locally (via notifyChange(Uri, .., ..))
    * */
    @Override
    public void onPerformSync(Account account, Bundle extras, String s, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.d(tag, this.getClass().getSimpleName() + " - onPerformSync for account - " + account.name);

        // Building a print of the extras we got
        StringBuilder sb = new StringBuilder();
        if (extras != null) {
            for (String key : extras.keySet()) {
                sb.append(key + "[" + extras.get(key) + "] ");
            }
        }

        Log.d(tag, this.getClass().getSimpleName() + " > onPerformSync for account[" + account.name + "]. Extras: " + sb.toString());

        //retrieve the auth token for the current account
        try
        {
            //retrieve the auth token
            //blocking method is ran synchronously since we're in background thread
            //third parameter - notifyAuthFailure - notify user with a notification when authentication fails (due to invalidated auth token perhaps)
            String authToken = accountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            //needed to create objects on parse.com
            String userObjectId = accountManager.getUserData(account, AccountGeneral.USERDATA_USER_OBJ_ID);

            ParseComServerAccessor pcsa = new ParseComServerAccessor();

            //get shows from the remote server
            List<TvShow> remoteShows = pcsa.getShows(authToken);

            //get shows from the local storage
            ArrayList<TvShow> localShows = new ArrayList<TvShow>();
            Cursor cursor = provider.query(TvShowsContract.CONTENT_URI, null, null, null, null);

            if(cursor != null)
            {
                while(cursor.moveToNext())
                {
                    localShows.add(TvShow.fromCursor(cursor));
                }
                cursor.close();
            }

            //see what local shows are missing from the remote
            ArrayList<TvShow> showsForRemote = new ArrayList<>();
            for(TvShow localShow : localShows)
            {
                if(!remoteShows.contains(localShow))
                {
                    showsForRemote.add(localShow);
                }
            }

            //see what remote shows ar emissing on local
            ArrayList<TvShow> showsForLocal = new ArrayList<>();
            for(TvShow remoteShow : remoteShows)
            {
                if(!localShows.contains(remoteShow))
                {
                    showsForLocal.add(remoteShow);
                }
            }

            //check if there is something to update to remote server
            if(showsForRemote.size() == 0)
            {
                Log.d(tag, this.getClass().getSimpleName() + " - onPerformSync - no data for remote.");
            }
            else
            {
                Log.d(tag, this.getClass().getSimpleName() + " - onPerformSync - updating data to remote!");

                for(TvShow showForRemote : showsForRemote)
                {
                    Log.d(tag, this.getClass().getSimpleName() + " - to remote: [" + showForRemote.name + "]");
                    pcsa.putShow(authToken, userObjectId, showForRemote);
                }
            }

            if(showsForLocal.size() == 0)
            {
                Log.d(tag, this.getClass().getSimpleName() + " - onPerformSync - no data for local.");
            }
            else
            {
                Log.d(tag, this.getClass().getSimpleName() + " - onPerformSync - updating data to local!");
                int i = 0;
                ContentValues[] values = new ContentValues[showsForLocal.size()];
                for(TvShow showForLocal : showsForLocal)
                {
                    Log.d(tag, this.getClass().getSimpleName() + " - to local: [" + showForLocal.name + "]");
                    values[i++] = showForLocal.getContentValues();
                }
                provider.bulkInsert(TvShowsContract.CONTENT_URI, values);
            }

            Log.d(tag, this.getClass().getSimpleName() + " - onPerformSync - finished syncing.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
