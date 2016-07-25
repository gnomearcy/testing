package hr.span.tmartincic.testing.esspresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityOne extends Activity
{
    private static final String tag = "FunctionalTesting";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_one);

        Log.d(tag, "EspressoAct - onCreate method");
        button = (Button) findViewById(R.id.activityone_btn);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(tag, "I am in button click listener.");
                startActivity(new Intent(ActivityOne.this, ActivityTwo.class));
            }
        });
    }
}
