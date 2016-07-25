package hr.span.tmartincic.implementation.implementation.dbscheme;

import android.net.Uri;

//Contract class defining URI components for various data
public class TvShowsContract
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.tmartincic.tvshow";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.tmartincic.tvshow";

    public static final String AUTHORITY = "hr.span.tmartincic.tvshows.provider";

    // content://<authority>/<path to type>
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/tvshows");

    public static final String TV_SHOW_ID = "_id";
    public static final String TV_SHOW_NAME = "name";
    public static final String TV_SHOW_YEAR = "year";
}
