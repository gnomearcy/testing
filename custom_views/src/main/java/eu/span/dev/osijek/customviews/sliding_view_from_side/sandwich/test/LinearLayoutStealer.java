package eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich.ObjectAnimatorPostman;


public class LinearLayoutStealer extends LinearLayout
{
    private static final String tag = "CustomViews";

    public LinearLayoutStealer(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenX = dm.widthPixels;
        Log.d(tag, "Screen size DP - " + screenX + "|" + dm.heightPixels);
        animationThreshold = screenX * percentage;
        animationEndValue = screenX * (1.0F - animationEndOffsetPercentage);
    }

    private float downX;
    private float moveX;
    private float upX;

    /** General window information */
    final float percentage = 0.5F; // %-amount of screen in X axis above which to start the view animation
    final float animationEndOffsetPercentage = 0.1F; //after right-animation end, make the animated view 10% visible from right side
    float screenX;
    float animationThreshold;
    float animationEndValue;
    boolean aboveThreshold;

    final static int INVALID_POINTER = -1;

    /**
     *  Id of the second finger (first pointer) on the screen
     *  By recording this Id in the ACTION_POINTER_DOWN action we can initiate view animation
     *  in ACTION_POINTER_UP when this finger is lifted up. In case there are multiple
     *  fingers on the screen, only by lifting the original pointer that initiated the dragging,
     *  which is this one, are we initiating the animation process.
     */
    int pointerDownId = INVALID_POINTER;
    int downId = INVALID_POINTER;
    boolean dragging = false;
    boolean animating = false;

    /**
     * This layout contains children which can receive DOWN action. The following MOVE actions
     * are intercepted here and stolen from children if the MOVE action made us move pass the
     * horizontal slope value. In that case, return true from this function for all subsequent
     * actions to end in the onTouchEvent of this parent. This parent will be animated horizontally
     */

    /**
     *  Since this implementation requires that two fingers initiate the drag movement, there is no
     *  need to keep track of the moved distance in ACTION_MOVE compared to the ACTION_DOWN to steal
     *  the gesture from all children of this view.
     */
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev)
//    {
//        switch(ev.getActionMasked())
//        {
//            /**
//             *  If a ViewGroup intercepts the initial DOWN event,
//             *  this event will also be passed to the ViewGroup's onTouchEvent.
//             */
//            case MotionEvent.ACTION_DOWN:
//                /** Don't intercept down events - children of this layout need a chance for the click listener */
//                downX = ev.getRawX();
//                Log.d(tag, "onIntercept - action down - downX - " + downX);
//                return false;
//
//            case MotionEvent.ACTION_MOVE:
//                /**
//                 *  Review the move distance since DOWN action.
//                 *  If we have moved passed the certain threshold, intercept the event.
//                 */
//                moveX = ev.getRawX();
//                Log.d(tag, "onIntercept moveX - " + moveX);
//
//                if(Math.abs(moveX - downX) >= thresholdX)
//                {
//                    /** All subsequent MOVE actions will be passed to the onTouchEvent callback */
//                    log(toAction(ev.getAction()) + " - onInterceptTouchEvent - I will intercept this now.");
//                    return false;
//                }
//                else
//                {
//                    log(toAction(ev.getAction()) + " - onInterceptTouchEvent - I'm still not intercepting.");
//                    return false;
//                }
//        }
//
//        return super.onInterceptTouchEvent(ev);
//    }



    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        //If we are in the process of animating the view to its final position, then just return false
        //to indicate that we're not interested in any more of the actions until animating is finished
        if(animating)
        {
            Log.d(tag, "I am animating, returning false...");
            return false;
        }

        switch(ev.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:

                //Record the Id of the first finger on the screen
                downId = ev.getPointerId(ev.getActionIndex());
                return true;

            case MotionEvent.ACTION_POINTER_DOWN:

                //For all subsequent fingers on the screen, do nothing
                if(dragging)
                {
                    return true;
                }

                //Record the Id of the second finger on the screen and initiate dragging mode
                pointerDownId = ev.getPointerId(ev.getActionIndex());
                dragging = true;
                downX = ev.getRawX();
                return true;

            case MotionEvent.ACTION_MOVE:

                //We still haven't received the second finger on the screen
                //Therefor don't do anything for this action
                if(pointerDownId == INVALID_POINTER)
                {
                    return true;
                }
//                if(!dragging)
//                {
//                    return true;
//                }
                moveX = ev.getRawX();

                if((upX + moveX - downX) >= 0)
                {
                    this.setTranslationX(upX + moveX - downX);
                }
                else
                {
                    this.setTranslationX(0.0F);
                    downX = moveX;
                }
                return true;

            case MotionEvent.ACTION_POINTER_UP:

                int currentId = ev.getPointerId(ev.getActionIndex());

                //If any of the initial two fingers are lifted up, animate the view
                if(currentId == pointerDownId || currentId == downId)
                {
                    animating = true;
                    animateView();
                }

                return true;

            case MotionEvent.ACTION_UP:

                //reset the state from ACTION DOWN
                downId = INVALID_POINTER;

                return true;
        }

        return super.onTouchEvent(ev);
    }

    private void animateView()
    {
        upX = getTranslationX();

        ObjectAnimatorPostman anim = new ObjectAnimatorPostman()
        {
            @Override
            public void animationEnded()
            {
                animating = false;
                dragging = false;
                downId = INVALID_POINTER;
                pointerDownId = INVALID_POINTER;

                //reset variables here
                if(aboveThreshold)
                {
                    upX = animationEndValue;
                }
                else
                {
                    upX = 0;
                }
            }

            @Override
            public void onAnimationProgress(int percentage)
            {

            }
        };

        anim.setTarget(this);
        anim.setPropertyName("translationX");
        anim.setDuration(250);
        anim.setInterpolator(new AccelerateInterpolator());

        if(upX >= animationThreshold)
        {
            aboveThreshold = true;
            anim.setFloatValues(animationEndValue);
        }
        else
        {
            aboveThreshold = false;
            anim.setFloatValues(0.0F);
        }

        anim.start();
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

        return "";
    }

    /**
     *  OBSERVATION 1:
     *
     *  on ActionPointerUp, the index of the last finger being lifted up is always 4.
     *  which means that if there are 9 fingers on the screen and you life the one with the
     *  index 4 up first, that index will be removed. upon removing the remaining 8 fingers
     *  the index of the 8th finger will always be 4 for some reason... (maybe because they
     *  assume that only one hand is used in the moving gesture)
     */
}
