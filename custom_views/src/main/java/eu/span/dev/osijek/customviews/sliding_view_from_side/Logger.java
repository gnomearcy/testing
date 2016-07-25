package eu.span.dev.osijek.customviews.sliding_view_from_side;

import android.util.Log;

public class Logger
{
    private static final boolean DEBUG = true;
    private static final String tag = "GlobalLog";

    public static void D(String message)
    {
        if(DEBUG)
        {
            Log.d(tag, message);
        }
    }
}
