package hr.span.tmartincic.implementation.implementation.dbscheme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TvShowDbHelper extends SQLiteOpenHelper
{
    private static final String tag = "SyncAdapter";

    //database info
    public static final String DATABASE_NAME = "tomo.db";
    public static final int DATABASE_VERSION = 1;

    //database scheme
    public static final String TVSHOWS_TABLE_NAME = "tvshows";
    public static final String TVSHOWS_COL_ID = "id";
    public static final String TVSHOWS_COL_NAME = "name";
    public static final String TVSHOWS_COL_YEAR = "year";

    //database creation sql statement
    public static final String DATABASE_CREATE = "create table "
            + TVSHOWS_TABLE_NAME + "(" +
            TVSHOWS_COL_ID + " integer   primary key autoincrement, " +
            TVSHOWS_COL_NAME + " text not null, " +
            TVSHOWS_COL_YEAR + " integer " +
            ");";

    //database upgrade sql statement
    public static final String DATABASE_DROP = "drop table if exists " + TVSHOWS_TABLE_NAME;

    public TvShowDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //create the database
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    /**
     * Used to upgrade the database schema
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        Log.d(tag, "Upgrading database from " + oldVersion + " to " + newVersion);
        sqLiteDatabase.execSQL(DATABASE_DROP);
        onCreate(sqLiteDatabase);
    }
}
