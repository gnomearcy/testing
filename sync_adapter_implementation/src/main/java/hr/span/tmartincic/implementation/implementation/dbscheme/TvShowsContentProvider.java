package hr.span.tmartincic.implementation.implementation.dbscheme;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.net.URI;

//provides CRUD operations on SQLiteDatabase
public class TvShowsContentProvider extends ContentProvider
{
    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String PATH = "tvshows";
    public static final int PATH_TOKEN = 100;
    public static final String PATH_FOR_ID = "tvshows/*";
    public static final int PATH_FOR_ID_TOKEN = 200;

    private static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TvShowsContract.AUTHORITY;
        matcher.addURI(authority, PATH, PATH_TOKEN);
        matcher.addURI(authority, PATH_FOR_ID, PATH_FOR_ID_TOKEN);
        return matcher;
    }

    private TvShowDbHelper dbHelper;

    @Override
    public boolean onCreate()
    {
        //retrieve the context this ContentProvider is running in
        Context ctx = getContext();
        dbHelper = new TvShowDbHelper(ctx);
        return true;
    }

    //returns data to the user
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = URI_MATCHER.match(uri);

        switch(match)
        {
            case PATH_TOKEN:
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(TvShowDbHelper.TVSHOWS_TABLE_NAME);
                return builder.query(
                        db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }

            case PATH_FOR_ID_TOKEN:
            {
                //converts the last-path-segment to long
                //content://authority/path/id -> id is the last-path-segment
                int tvShowId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(TvShowDbHelper.TVSHOWS_TABLE_NAME);
                builder.appendWhere(TvShowDbHelper.TVSHOWS_COL_ID + "=" + tvShowId);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }

            default:
                return null;
        }
    }

    //returns the MIME type of the data in content provider.
    @Override
    public String getType(Uri uri)
    {
        int match = URI_MATCHER.match(uri);

        switch(match)
        {
            case PATH_TOKEN:
                return TvShowsContract.CONTENT_TYPE_DIR; //return vnd.android.cursor.dir/...
            case PATH_FOR_ID_TOKEN:
                return TvShowsContract.CONTENT_ITEM_TYPE; //return vnd.android.cursor.item/...
            default:
                throw new UnsupportedOperationException("getType error - match = " + match);
        }
    }

    //insert a new row into the table
    //returns the Uri of the newly inserted item
    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch(token)
        {
            case PATH_TOKEN:
            {
                //returns -1 if an error occurs.
                long newId = db.insert(TvShowDbHelper.TVSHOWS_TABLE_NAME, null, contentValues);
                if(newId != -1)
                {
                    /*
                    * parameters: Uri of the changed row, ContentObserver instance
                    * The observer that originated the change, may be null. The observer that originated the change will only receive the notification
                    * if it has requested to receive self-change notifications by implementing deliverSelfNotifications() to return true.*/
                    this.getContext().getContentResolver().notifyChange(uri, null);
                }
                //concatenate the newId parameter to the given Uri
                return TvShowsContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(newId)).build();
            }
            default:
                throw new UnsupportedOperationException("Uri " + uri + " is not supported.");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        switch(token)
        {
            case PATH_TOKEN:
            {
               rowsDeleted = db.delete(TvShowDbHelper.TVSHOWS_TABLE_NAME, selection, selectionArgs);
                break;
            }

            case PATH_FOR_ID_TOKEN:
            {
                String tvShowIdWhereClause = TvShowDbHelper.TVSHOWS_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(TvShowDbHelper.TVSHOWS_TABLE_NAME, tvShowIdWhereClause, selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if(rowsDeleted != -1) //there was a change
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;
    }
}
