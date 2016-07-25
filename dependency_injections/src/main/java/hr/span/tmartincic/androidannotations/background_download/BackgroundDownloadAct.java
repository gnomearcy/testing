package hr.span.processor.androidannotations.background_download;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import hr.span.processor.androidannotations.R;


@EActivity(R.layout.activity_background_download)
@OptionsMenu(R.menu.menu_background_download)
public class BackgroundDownloadAct extends Activity
{
    @ViewById
    ProgressBar progressBar;
    @ViewById
    TextView progressDisplay;

    @Background
    @OptionsItem
    void startDownload()
    {
        for(int i = 1 ; i < 11; i++)
        {
            publishResult(i);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @UiThread
    void publishResult(int progress)
    {
        if(progress == 10)
        {
            progressDisplay.setText("Done");
        }
        progressBar.setProgress(progress * 10);
    }
}
