package eu.span.dev.osijek.customviews.sliding_view_from_side.imagebutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class ImageButton extends RelativeLayout
{
    public ImageButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int i = getChildCount();
        for(int j = 0; j < i; i++)
        {
            getChildAt(j).dispatchTouchEvent(event);
        }
        return false;
    }
}
