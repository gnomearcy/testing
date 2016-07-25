package eu.span.devosijek.databinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import eu.span.devosijek.databinding.databinding.MainActivityBinding;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        MainActivityBinding binder = DataBindingUtil.setContentView(this, R.layout.main_activity);
        User user = new User("Tomo", "Test");
        binder.setUser(user);
    }
}
