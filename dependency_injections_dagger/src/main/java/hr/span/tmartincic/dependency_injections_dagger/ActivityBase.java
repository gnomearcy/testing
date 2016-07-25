package hr.span.tmartincic.dependency_injections_dagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityBase extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_base);
    }

    protected void log(String message)
    {
        Log.d(ActivityEntry.TAG, message);
    }

    protected void breakpointPlaceholder()
    {
    }
}