package eu.span.devosijek.memoryanalysis;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.android.debug.hv.ViewServer;

public class BaseActivity extends Activity
{
    boolean useApplicationContext = true;
    boolean doContextLeak = true;
    String largeStringVariable;

    void doContextLeak()
    {
        if(doContextLeak)
        {
            StringBuilder sb = new StringBuilder();

            final int count = 300000;

            for(int j = 0; j < count; j++)
            {
                sb.append(j);
            }

            largeStringVariable = sb.toString();
        }
    }

    void callNext(Class<?> next)
    {
        Intent i = new Intent();
        if(useApplicationContext)
        {
            i.setClass(getApplicationContext(), next);
        }
        else
        {
            i.setClass(this, next);
        }

        startActivity(i);

        finish();
    }

    void loadImage()
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.landscape_small, opts);
        ImageView imageTarget = (ImageView) findViewById(R.id.testimage);
        imageTarget.setImageBitmap(bmp);
    }

    @Override
    protected void onPause()
    {
        Log.d("Testing", this.getClass().getSimpleName() + " onPause.");
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        Log.d("Testing", this.getClass().getSimpleName() + " onResume.");
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy()
    {
//        Log.d("Testing", this.getClass().getSimpleName() + " onDestroy.");
//        super.onDestroy();
//
//        ImageView image = (ImageView) findViewById(R.id.testimage);
//        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
//
//        if(drawable != null)
//        {
//            Log.d("Testing", this.getClass().getSimpleName() + " - drawable not null");
//            Bitmap bmp = drawable.getBitmap();
//            Drawable.Callback ck = drawable.getCallback();
//
//            if(ck != null)
//            {
//                Log.d("Testing", this.getClass().getSimpleName() + " - callback not null - type: " + ck.getClass().getSimpleName());
//                drawable.setCallback(null);
//                image.setImageDrawable(null);
//            }
//            else
//                Log.d("Testing", this.getClass().getSimpleName() + " - callback is null");
//
//            if(!bmp.isRecycled())
//            {
//                Log.d("Testing", this.getClass().getSimpleName() + " - recycling bitmap.");
//            }
//            else
//            {
//                bmp.recycle();
//                Log.d("Testing", this.getClass().getSimpleName() + " - bitmap is already recycled.");
//            }
//        }
//        else
//        {
//            Log.d("Testing", this.getClass().getSimpleName() + " - drawable null");
//        }

        super.onDestroy();

        ViewGroup v = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);

        Log.d("Testing", "type - " + v.getClass().getSimpleName());
        printKids(v);

        ViewServer.get(this).removeWindow(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    void printKids(ViewGroup v)
    {
        int nr = v.getChildCount();

        for(int i = 0; i < nr; i++)
        {
            if(v.getChildAt(i) instanceof ViewGroup)
                printKids((ViewGroup) v.getChildAt(i));

            if(v.getChildAt(i).hasOnClickListeners())
            {
                Log.d("Testing", "Removing click listener on kid - " + v.getChildAt(i).getId() +
                        " of type - " + v.getChildAt(i).getClass().getSimpleName());
                v.getChildAt(i).setOnClickListener(null);
            }
            Log.d("Testing", "Kid - " + v.getChildAt(i).getClass().getSimpleName());
        }

    }
}
