package eu.span.dev.osijek.supportlibrarytests;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActivityLauncher extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_launcher);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content_container, new FragmentOne(), "bla").commit();

        Intent service = new Intent();
        ComponentName c = new ComponentName("eu.span.dev.osijek.spanroomassistant.updater", "eu.span.dev.osijek.spanroomassistant.updater.UpdaterService");
        service.setComponent(c);

        startService(service);
        int i = 0;
    }

    public static class FragmentOne extends Fragment
    {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.fragment_one, container, false);
        }
    }
}
