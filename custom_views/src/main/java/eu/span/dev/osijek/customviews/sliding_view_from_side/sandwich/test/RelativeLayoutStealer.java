package eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class RelativeLayoutStealer extends RelativeLayout
{
    private static final String tag = "CustomViews";

    public RelativeLayoutStealer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private void log(String message)
    {
        Log.d(tag, this.getClass().getSimpleName() + " - " + message);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch(ev.getActionMasked())
        {
            /**
             *  If a ViewGroup intercepts the initial DOWN event,
             *  this event will also be passed to the ViewGroup's onTouchEvent.
             */
            case MotionEvent.ACTION_DOWN:
                log(toAction(ev.getAction()) + " - onInterceptTouchEvent - I don't want to intercept this action");
                return false;

            case MotionEvent.ACTION_MOVE:
                /**
                 *  Review the move distance since DOWN action.
                 *  If we have moved passed the certain threshold, intercept the event.
                 */
//                log(toAction(ev.getAction()) + " - onInterceptTouchEvent - I don't want to intercept this action");
                return false;

            case MotionEvent.ACTION_POINTER_DOWN:
                log(toAction(ev.getAction()) + " - onInterceptTouchEvent");
                return true;
        }

        return false;
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                log(toAction(event.getAction()) + " - onTouchEvent");
                return false;
            case MotionEvent.ACTION_MOVE:
                log(toAction(event.getAction()) + " - onTouchEvent");
                return false;
            case MotionEvent.ACTION_POINTER_DOWN:
                log(toAction(event.getAction()) + " - onTouchEvent");
                return true;
        }

        return super.onTouchEvent(event);
    }

    private String toAction(int action)
    {
        switch(action)
        {
            case 0:
                return "ACTION_DOWN";
            case 1:
                return "ACTION_UP";
            case 2:
                return "ACTION_MOVE";
            case 3:
                return "ACTION_CANCEL";
            case 4:
                return "ACTION_OUTSIDE";
            case 5:
                return "ACTION_POINTER_DOWN";
            case 6:
                return "ACTION_POINTER_UP";
            case 7:
                return "ACTION_HOVER_MOVE";
        }

        return MotionEvent.actionToString(action);
    }
}
