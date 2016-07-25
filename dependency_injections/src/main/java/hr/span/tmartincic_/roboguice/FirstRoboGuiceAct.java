package hr.span.tmartincic_.roboguice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import hr.span.processor.androidannotations.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class FirstRoboGuiceAct extends RoboActivity
{
    @InjectView(R.id.textview01)
    TextView textview01;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_first_robo_guice);
        textview01.setText("Hello");
    }
}
