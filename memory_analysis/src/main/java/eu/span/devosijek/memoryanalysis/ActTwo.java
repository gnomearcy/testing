package eu.span.devosijek.memoryanalysis;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.joda.time.DateTime;


public class ActTwo extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("Testing", this.getClass().getSimpleName() + " onCreate.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_two);

        findViewById(R.id.actnbtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i = new Intent();
//                if(useApplicationContext)
//                    i.setClass(getApplicationContext(), ActThree.class);
//                else
//                    i.setClass(ActTwo.this, ActThree.class);
//
//                startActivity(i);

                callNext(ActThree.class);
            }
        });

        doContextLeak();
        loadImage();
    }


}
