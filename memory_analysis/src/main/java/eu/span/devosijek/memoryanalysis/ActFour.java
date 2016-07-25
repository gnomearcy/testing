package eu.span.devosijek.memoryanalysis;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ActFour extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("Testing", this.getClass().getSimpleName() + " onCreate.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_four);

        findViewById(R.id.actnbtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i = new Intent();
//
//                if(useApplicationContext)
//                    i.setClass(getApplicationContext(), ActFive.class);
//                else
//                    i.setClass(ActFour.this, ActFive.class);
//
//                startActivity(i);

                callNext(ActFive.class);
            }
        });

        doContextLeak();
        loadImage();
    }


}
