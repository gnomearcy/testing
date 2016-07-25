package eu.span.devosijek.memoryanalysis;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class ActSix extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_six);

        findViewById(R.id.secondbtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ImageView target = (ImageView) findViewById(R.id.testimage);
//                target.setImageDrawable(null);

                Bitmap bmp = ((BitmapDrawable) target.getDrawable()).getBitmap();
                if(!bmp.isRecycled())
                {
                    Log.d("Testing", "bitmap is getting recycled");
                    bmp.recycle();
                    target.getDrawable().setCallback(null);
                    target.setImageDrawable(null);
                }
                else
                    Log.d("Testing", "bitmap is recycled");
            }
        });

        loadImage();
    }


}
