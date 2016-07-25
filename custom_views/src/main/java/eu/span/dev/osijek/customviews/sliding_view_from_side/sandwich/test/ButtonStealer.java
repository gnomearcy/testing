package eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class ButtonStealer extends Button
{
    String tag = "CustomViews";

    public ButtonStealer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch(event.getActionMasked())
                {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(tag, "button - action down");
                        return false;
                    case MotionEvent.ACTION_MOVE:
//                        Log.d(tag, "button - action move");
                        return false;
                    case MotionEvent.ACTION_UP:
                        Log.d(tag, "button - action up");
                        return false;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(tag, "button action cancel");
                        return false;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        Log.d(tag, "button action pointer down");
                        return false;
                }
                return false;
            }
        });
    }



}
