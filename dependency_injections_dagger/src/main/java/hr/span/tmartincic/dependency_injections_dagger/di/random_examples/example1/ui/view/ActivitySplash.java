package eu.span.dev.osijek.di.scoping.data.ui.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import eu.span.dev.osijek.di.scoping.R;

public class ActivitySplash extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_splash);
    }
}
