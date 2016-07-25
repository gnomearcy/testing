package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example3;

import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import javax.inject.Inject;

import eu.span.dev.osijek.dimvp.demo.components.ComponentActivityJokes;
import eu.span.dev.osijek.dimvp.demo.components.ComponentServices;
import eu.span.dev.osijek.dimvp.demo.components.DaggerComponentActivityJokes;

public class ActivityJokes extends AppCompatActivity
{
    private static final String tag = "MVP_DI";

    @Inject
    public SharedPreferences prefs;

    @Inject
    LayoutInflater inflater;

    @Inject
    WifiManager wifiManager;

    ComponentActivityJokes component;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_jokes);

        ComponentServices comp = ((ApplicationChuckNorris) getApplication()).getGlobal();
        component = DaggerComponentActivityJokes.builder().componentServices(comp).build();

        component.inject(this);
    }
}
