package hr.span.tmartincic.testing.esspresso;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class EspressoTwo extends Activity
{
    private static final String tag = "EspressoTesting";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(tag, "EspressoTwo - onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso_two);
    }
}
