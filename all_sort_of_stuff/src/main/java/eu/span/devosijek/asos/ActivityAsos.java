package eu.span.devosijek.asos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityAsos extends AppCompatActivity
{
    protected int red;
    protected int green;
    protected int blue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_asos);
    }
}
