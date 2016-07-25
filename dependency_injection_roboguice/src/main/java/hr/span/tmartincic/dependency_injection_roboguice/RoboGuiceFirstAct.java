package hr.span.processor.dependency_injection_roboguice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_robo_guice_first)
public class RoboGuiceFirstAct extends RoboActivity
{
    @Inject
    SimplePOJO pojo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        Toast.makeText(this, pojo.ispisi(), Toast.LENGTH_SHORT).show();
        Log.d("RoboGuice", "Rezultat - " + pojo.ispisi());
    }
}
