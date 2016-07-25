package hr.span.tmartincic.testing.esspresso;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class ActivityTwo extends Activity
{
    private static final String tag = "FunctionalTesting";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(tag, "ActivityTwo - onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_two);
    }
}
