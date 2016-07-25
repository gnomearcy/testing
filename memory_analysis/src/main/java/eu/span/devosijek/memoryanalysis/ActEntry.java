package eu.span.devosijek.memoryanalysis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.debug.hv.ViewServer;

import org.joda.time.DateTime;

public class ActEntry extends BaseActivity
{
//    static ImageView image;

//    static LeakClass leakedVariable123;
//
//    class LeakClass
//    {
//        int variable;
//        LeakClass(){}
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("Testing", this.getClass().getSimpleName() + " onCreate.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_entry);

        findViewById(R.id.actnbtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                if(useApplicationContext)
//                    i.setClass(getApplicationContext(), ActTwo.class);
//                else
//                    i.setClass(ActEntry.this, ActTwo.class);
//
//                startActivity(i);

                callNext(ActTwo.class);
            }
        });

        findViewById(R.id.secondbtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ImageView target = (ImageView) findViewById(R.id.testimage);
//                target.setImageDrawable(null);

                Bitmap bmp = ((BitmapDrawable)target.getDrawable()).getBitmap();
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

//        doContextLeak();
//        loadImage();

        ViewServer.get(this).addWindow(this);
    }


//goes into on destroy
//    Bitmap bmp = ((BitmapDrawable)image.getDrawable()).getBitmap();
//
//        if(bmp != null && !bmp.isRecycled())
//        {
//            Log.d("Testing", "Recycling...");
//            bmp.recycle();
//        }

//        image.getDrawable().setCallback(null);
//        image = null;


    //    static class Adapter extends ArrayAdapter<String>
//    {
//        Context context;
//
//        public Adapter(Context context, int resource)
//        {
//            super(context, resource);
//            this.context = context;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//            TextView v = new TextView(context);
//            v.setText();
//        }
//    }
}
