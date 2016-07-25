package eu.span.dev.osijek.customviews.sliding_view_from_side;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import eu.span.dev.osijek.customviews.R;
import in.championswimmer.sfg.lib.SimpleFingerGestures;

public class TestCase1 extends AppCompatActivity
{
    private static final String tag = "CustomViews";
    Button btnLR;
    Button btnUP;
    ImageView area;

    View.OnTouchListener swipeListener;
    ViewTreeObserver.OnPreDrawListener vtoPreDrawListener;

    /** Left to right button parameters */
    int btnWidth;
    int btnHeight;
    int positionX; //position on the screen - X
    int positionY; //position on the screen - Y
    int screenWidth;
    int screenHeight;

    float threshholdX; //minimum value after which to animate the button to the final position
    final float percent = 0.5F;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.imagebutton_test1);
//        instantiateLayout1();
//        instantiateLayout2();
//        instantiateLayout3();

        /** Get information about the screen dimensions */
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        Log.d(tag, "Screen specs - w/h [" + screenWidth + "|" + screenHeight + "]");
    }

    private void instantiateLayout1()
    {
        setContentView(R.layout.testcase1);
        btnLR = (Button) findViewById(R.id.button_leftright);
        btnUP = (Button) findViewById(R.id.button_updown);
        area = (ImageView) findViewById(R.id.swipe_area);
        area.setOnTouchListener(getSwipeTouchListener());
        ViewTreeObserver vto = btnLR.getViewTreeObserver();
        vto.addOnPreDrawListener(getVTOPreDrawListener());
    }

    private void instantiateLayout3()
    {
        setContentView(R.layout.testcase1_fragments);
        SimpleFingerGestures sfg = new SimpleFingerGestures();
        sfg.setDebug(true);
        sfg.setConsumeTouchEvents(true);

        sfg.setOnFingerGestureListener(new SimpleFingerGestures.OnFingerGestureListener()
        {
            @Override
            public boolean onSwipeUp(int fingers, long gestureDuration, double gestureDistance)
            {
                Log.d(tag, "You swiped " + fingers + " fingers  up " + gestureDuration
                        + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeDown(int fingers, long gestureDuration, double gestureDistance)
            {
                Log.d(tag, "You swiped " + fingers + " fingers  down " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeLeft(int fingers, long gestureDuration, double gestureDistance)
            {
                Log.d(tag, "You swiped" + fingers + "fingers left" + gestureDuration + "milliseconds" + gestureDistance + "pixels far");
                return false;
            }

            @Override
            public boolean onSwipeRight(int fingers, long gestureDuration, double gestureDistance)
            {
                Log.d(tag, "You swiped " + fingers + " fingers  right " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onPinch(int fingers, long gestureDuration, double gestureDistance)
            {
                Log.d(tag, "You pinched " + fingers + " fingers " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onUnpinch(int fingers, long gestureDuration, double gestureDistance)
            {
                Log.d(tag, "You unpinched " + fingers + "fingers" + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onDoubleTap(int fingers)
            {
                Log.d(tag, "You double tapped");
                return false;
            }
        });

        findViewById(R.id.gesturelibcontainer).setOnTouchListener(sfg);
    }

    ViewGroup toAnimate;

    private void instantiateLayout2()
    {
        setContentView(R.layout.testcase1_fragments);
    }

    final int limit = 20;
    int i = limit;

    float initialX;
    float initialY;
    float currentX; // current swipe X
    float currentY;
    float currentBtnX = positionX;

    boolean animate;

    private View.OnTouchListener getSwipeTouchListener()
    {
        if(swipeListener == null)
        {
            swipeListener = new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    int pointerCount = event.getPointerCount();
                    int action = event.getAction();
                    int actionIndex = event.getActionIndex();
                    int actionMasked = event.getActionMasked();

                    boolean print = false;
                    i--;
                    if(i == 0)
                    {
                        Log.d(tag, "Action[" + action + "] | ActionIndex[" + actionIndex + "] | ActionMasked["
                                + actionMasked + "] | PointerCount[" + pointerCount + "]");
                        if(event.getPointerCount() > 1)
                        {
                            Log.d(tag, "Multiple touch!");
                        }

                        i = limit;
                        print = true;
                    }

                    switch(event.getActionMasked())
                    {
                        case MotionEvent.ACTION_DOWN:
                            Log.d(tag, "Action down");
                            initialX = event.getX();
                            initialY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(print)
                                Log.d(tag, "Action move");

                            currentX = event.getX();
                            if(currentX >= initialX) //only support left to right slide
                            {
                                float differenceX = Math.abs(currentX - initialX);
                                btnLR.setTranslationX(currentBtnX + differenceX);
                            }

                            break;
                        /**
                         *  ACTION_POINTER_DOWN and UP are passed to onTouchEvent
                         *  callback whenever a secondary pointer goes down or up.
                         */
                        case MotionEvent.ACTION_POINTER_DOWN:
                            Log.d(tag, "Action pointer down");
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            Log.d(tag, "Action pointer up");
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d(tag, "Action up");
                            currentBtnX = btnLR.getTranslationX();
                            break;
                    }

                    /** Tell the framework to pass all coming events to this listener */
                    return true;
                }
            };
        }

        return swipeListener;
    }

    private ViewTreeObserver.OnPreDrawListener getVTOPreDrawListener()
    {
        if(vtoPreDrawListener == null)
        {
            vtoPreDrawListener = new ViewTreeObserver.OnPreDrawListener()
            {
                @Override
                public boolean onPreDraw()
                {
                    btnLR.getViewTreeObserver().removeOnPreDrawListener(this);
                    btnWidth = btnLR.getMeasuredWidth();
                    btnHeight = btnLR.getMeasuredHeight();
                    int[] pos = new int[2];
                    btnLR.getLocationOnScreen(pos);
                    positionX = pos[0];
                    positionY = pos[1];

                    Log.d(tag, "Btn specs - onscreen [" + positionX + "|" + positionY +
                            "] | w/h [" + btnWidth + "|" + btnHeight + "]");

                    /** Screen specs are already calculated */
                    threshholdX = screenWidth * percent - ( btnWidth / 2.0f );
                    return true;
                }
            };
        }

        return vtoPreDrawListener;
    }

}
