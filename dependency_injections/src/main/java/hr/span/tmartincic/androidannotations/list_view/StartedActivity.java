package hr.span.processor.androidannotations.list_view;

import android.app.Activity;
import android.app.NotificationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.SystemService;

import java.util.ArrayList;

import hr.span.processor.androidannotations.R;

@EActivity(R.layout.activity_started)
@OptionsMenu(R.menu.menu_started)
public class StartedActivity extends Activity
{
    @Extra
    String someExtra;

    @Extra
    ArrayList<String> someList;

    @AfterExtras
    void somethingAfterExtras()
    {
        Log.d("AndroidAnnotations", "Extra " + someExtra);

        for(String ime : someList)
        {
            Log.d("AndroidAnnotations", "Record - " + ime);
        }
    }

    @OptionsItem(R.id.action_settings)
    void someMenuId()
    {

    }

}
