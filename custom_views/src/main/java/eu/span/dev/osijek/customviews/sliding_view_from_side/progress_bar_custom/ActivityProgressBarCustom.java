package eu.span.dev.osijek.customviews.sliding_view_from_side.progress_bar_custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import eu.span.dev.osijek.customviews.R;

public class ActivityProgressBarCustom extends AppCompatActivity
{

    ProgressBar progress;
    Button plus;
    Button minus;
    int currentProgress = 0;
    int delta = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_progress_bar_custom);

        progress = (ProgressBar) findViewById(R.id.progressbar);
        plus = (Button) findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(currentProgress + delta > 100)
                {
                    return;
                }

                currentProgress += delta;
                progress.setProgress(currentProgress);
            }
        });

        minus = (Button) findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(currentProgress - delta < 0)
                {
                    return;
                }

                currentProgress -= delta;
                progress.setProgress(currentProgress);
            }
        });
    }
}
