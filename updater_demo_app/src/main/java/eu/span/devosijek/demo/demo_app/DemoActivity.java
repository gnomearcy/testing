package eu.span.devosijek.demo.demo_app;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DemoActivity extends AppCompatActivity
{
    private static final String tag = "Updater";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewById(R.id.version_view).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(tag, "Onclick - starting service...");
                Intent i = new Intent();
                i.setAction("eu.span.devosijek.demo.updater.CHECK_UPDATE");
                startService(i);
            }
        });
    }
}
