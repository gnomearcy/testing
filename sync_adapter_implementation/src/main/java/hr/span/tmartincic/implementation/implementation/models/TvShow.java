package hr.span.tmartincic.implementation.implementation.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

import hr.span.tmartincic.implementation.implementation.dbscheme.TvShowDbHelper;

//POJO representing a tv show
public class TvShow implements Serializable
{
    public String name;
    public int year;

    public TvShow(String name, int year)
    {
        this.name = name;
        this.year = year;
    }

    /**
     * Wraps this object's properties into ContentValues
     * Used in ContentProvider operations
     * */
    public ContentValues getContentValues()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TvShowDbHelper.TVSHOWS_COL_NAME, this.name);
        contentValues.put(TvShowDbHelper.TVSHOWS_COL_YEAR, this.year);

        return contentValues;
    }

    // Create a TvShow object from a cursor
    public static TvShow fromCursor(Cursor curTvShows)
    {
        String name = curTvShows.getString(curTvShows.getColumnIndex(TvShowDbHelper.TVSHOWS_COL_NAME));
        int year = curTvShows.getInt(curTvShows.getColumnIndex(TvShowDbHelper.TVSHOWS_COL_YEAR));

        return new TvShow(name, year);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvShow tvShow = (TvShow) o;

        if (year != tvShow.year) return false;
        if (!name.equals(tvShow.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return name + " (" + year + ")";
    }
}
