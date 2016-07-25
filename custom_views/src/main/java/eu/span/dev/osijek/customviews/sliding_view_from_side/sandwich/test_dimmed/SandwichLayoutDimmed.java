package eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich.test_dimmed;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import eu.span.dev.osijek.customviews.R;
import eu.span.dev.osijek.customviews.sliding_view_from_side.Logger;
import eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich.ObjectAnimatorPostman;

/**
 *  This view requires both {@link android.R.attr#layout_width} and {@link android.R.attr#layout_height}
 *  with value match_parent. Also, explicit XML defined children of this ViewGroup cause a RuntimeException.
 *
 *  Current implementation allows variable ending of the animation only on the right side.
 *  In the right-to-left scenario, the animation will always finish on the left edge of the screen
 *  which is equivalent to 0.0F as the ending value of the translation animation.
 */
public class SandwichLayoutDimmed extends RelativeLayout
{
    private static final String TAG = "SandwichLayout";

    /** Global context */
    private final ViewGroup me;
    private OnTouchListener bottomChildTouchListener;
    private OnTouchListener topChildTouchListener;
    private ObjectAnimatorPostman animator;
    private ObjectAnimator dimmer;
    private View dimmedView;

    private static final int ANIMATOR_DURATION = 250;
    private static final Interpolator ANIMATOR_INTERPOLATOR;

    static
    {
        ANIMATOR_INTERPOLATOR = new LinearInterpolator();
    }

    /**
     *  Platform specific value for match_parent attribute value.
     *
     *  From platforms/android-19/data/res/values/attrs.xml
     *
     *  <declare-styleable name="ViewGroup_Layout">
     *      <attr name="layout_width" format="dimension">
     *          <enum name="match_parent" value="-1" />
     *      </attr>
     */
    private static final int MATCH_PARENT = -1;

    private static final int INVALID_DIMENSION = -123;

    private float downX;
    private float moveX;
    private float upX;

    /** General window information */

    /**
     *  Percentage which is used to calculate the threshold values for animation.
     *  Currently by moving above 25% of screen's width in case of left-to-right gesture, an animation
     *  animates the top child out of the window to the right. Likewise, by dragging the top child
     *  for 25% of screen's width in case of right-to-left gesture, the top child is animated back to
     *  its' original position.
     */
    private static final float percentage = 0.25F;

    /**
     *  A value above which left-to-right animation is triggered.
     *  Percentage of screens' width.
     *  Calculated as { screen width } * { percentage }
     */
    private float thresholdToRight;

    /**
     *  A value below which right-to-left animation is triggered.
     *  Percentage of screens' width.
     *  Calculated as { screen width } * { 100% - percentage }
     */
    private float thresholdToLeft;

    /**
     *  End value which to animate to the top child in left-to-right animation.
     *  Usually holds the screens' width value.
     */
    private float animationEndValueToRight;

    /**
     *  End value which to animate to the top child in right-to-left animation.
     *  Current implementation hardcodes the animation to end at the left edge of the screen.
     */
    private static final float ANIMATION_END_VALUE_TO_LEFT = 0.0F;

    /**
     *  Constant used to tweak the {@link SandwichLayoutDimmed#animationEndValueToRight}
     */
    private static final float animationEndOffsetPercentage = 0.05F;

    /**
     *  If the animation changes the position of the top child from left to right or right to left
     *  mark the top childs' position as dirty to update this parents' state at animation end
     */
    private boolean dirty;

    /**
     *  Enum container which holds the possible starting points of an animation.
     */
    private enum AnimationOrigin
    {
        FROM_LEFT,
        FROM_RIGHT
    }

    /**
     *  Enum defining from whence the animation started.
     */
    private AnimationOrigin currentOrigin;

    /**
     *  Id of the second finger (first pointer) on the screen
     *  By recording this Id in the ACTION_POINTER_DOWN action we can initiate view animation
     *  in ACTION_POINTER_UP when this finger is lifted up. In case there are multiple
     *  fingers on the screen, only by lifting the original pointer that initiated the dragging,
     *  which is this one, are we initiating the animation process.
     */
    private static final int INVALID_POINTER = -1;

    private int pointerDownId = INVALID_POINTER;
    private int downId = INVALID_POINTER;

    /**
     *  State flag indicating if the top child is being dragged horizontally.
     */
    private boolean dragging = false;

    /**
     *  State flag indicating if the top child is being animated.
     *  During the animation, action events feeding to the onTouchEvent of the top child are ignored.
     */
    private boolean animating = false;

    /**
     *  After "enableDrag" percent of width, disable drag motion in left-to-right drag.
     *  This behaviour is disabled when revealing hidden top view and enabled when the top view
     *  is fully visible.
     */
    private static final float enableDrag = 0.3F;

    private int horizontalThreshold;

    /**
     *  The width of the screen equivalent to the visible horizontal part of the bottom child when
     *  the top child is fully revealed ie. when the left-to-right animation ended and revealed the
     *  bottom child.
     *
     *  It is used to correctly calculate the starting alpha for the dimming animation.
     *  It is equivalent to the {@link SandwichLayoutDimmed#animationEndValueToRight} in current implementation.
     */
    private float dimmWidth;

    /**
     *  Enum paired with OnAnimationEnded interface delegates animation end callback
     *  to the activity hosting this view as its' content
     */
    public enum VisibleChild
    {
        BOTTOM,
        TOP
    }

    public interface OnAnimationEnded
    {
        void whichOneIsVisible(VisibleChild child);
    }

    private OnAnimationEnded hostActivity;

    public void setHostActivity(OnAnimationEnded newHost)
    {
        this.hostActivity = newHost;
    }

    public OnAnimationEnded getHostActivity()
    {
        return this.hostActivity;
    }

    public SandwichLayoutDimmed(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        me = this;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SandwichLayout);
        int rootWidth = array.getLayoutDimension(R.styleable.SandwichLayout_android_layout_width, INVALID_DIMENSION);
        int rootHeight = array.getLayoutDimension(R.styleable.SandwichLayout_android_layout_height, INVALID_DIMENSION);

        if(rootWidth == INVALID_DIMENSION || rootHeight == INVALID_DIMENSION)
        {
            throw new RuntimeException("Error occured while obtaining layout dimensions " +
                    "for android:layout_width or android:layout_height attributes");
        }

        /** Handle root dimension attributes mismatch */
        if(rootWidth != MATCH_PARENT || rootHeight != MATCH_PARENT)
        {
            throw new RuntimeException(
                    this.getClass().getSimpleName() + " " +
                    "- both android:layout_width and android:layout_height " +
                    "attributes need to be set to match_parent");
        }

        initialize(context);
        array.recycle();
    }

    private void initialize(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float screenX = dm.widthPixels;
        Logger.D("Width pixels - " + screenX);
        Logger.D("Height pixels - " + dm.heightPixels);
        thresholdToRight = percentage * screenX;
        thresholdToLeft = ( 1.0F - percentage ) * screenX;
        animationEndValueToRight = screenX * ( 1.0F - animationEndOffsetPercentage );
        dimmWidth = animationEndValueToRight;
        dimmer = getDimmer();
        animator = getPostman();
        currentOrigin = AnimationOrigin.FROM_LEFT;

        horizontalThreshold = (int) (screenX * enableDrag);

        Logger.D("===============================");
        Logger.D("Threshold to right - " + thresholdToRight);
        Logger.D("Threshold to left - " + thresholdToLeft);
        Logger.D("AnimationEndValueToRight - " + animationEndValueToRight);
        Logger.D("AnimationEndValueToLeft - " + ANIMATION_END_VALUE_TO_LEFT);
        Logger.D("Animation end offset [%] - " + animationEndOffsetPercentage);
//        Logger.D("Animation start offset [%] - " + animationStartOffsetPercentage);
        Logger.D("horizontal threshold - " + horizontalThreshold);
        Logger.D("===============================");
    }

    private OnTouchListener getBottomChildTouchListener()
    {
        if(bottomChildTouchListener == null)
        {
            bottomChildTouchListener = new OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent ev)
                {
                    // Delegate the touch event to the top child
                    return me.getChildAt(2).dispatchTouchEvent(ev);
                }
            };
        }

        return bottomChildTouchListener;
    }

    private OnTouchListener getTopChildTouchListener()
    {
        if(topChildTouchListener == null)
        {
            topChildTouchListener = new OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent ev)
                {
                    // If we are in the process of animating the view to its final position,
                    // then just return false to indicate that we're not interested in any more
                    // of the actions until animation is finished
                    if(animating)
                    {
                        return false;
                    }

                    switch(ev.getActionMasked())
                    {
                        case MotionEvent.ACTION_DOWN:

                            //Record the Id of the first finger on the screen
                            downId = ev.getPointerId(ev.getActionIndex());

                            if(currentOrigin == AnimationOrigin.FROM_RIGHT)
                            {
                                return true;
                            }

                            if(ev.getRawX() >= horizontalThreshold)
                            {
                                return false;
                            }
//                            return true;
                            break;

                        case MotionEvent.ACTION_POINTER_DOWN:

                            //For all subsequent fingers on the screen, do nothing
                            if(dragging)
                            {
                                return true;
                            }

                            pointerDownId = ev.getPointerId(ev.getActionIndex());
                            dragging = true;
                            downX = ev.getRawX();
//                            return true;

                            break;

                        case MotionEvent.ACTION_MOVE:

                            //We still haven't received the second finger on the screen
                            //Therefor don't do anything for this action
                            if(!dragging)
                            {
                                return true;
                            }

                            moveX = ev.getRawX();

                            if((upX + moveX - downX) >= 0)
                            {
                                v.setTranslationX(upX + moveX - downX);
                            }
                            else
                            {
                                v.setTranslationX(0.0F);
                                downX = moveX;
                            }

                            // Dimming.
                            // Max width for the alpha is the width of the screen where animation
                            // starts and ends. Currently, animation end value is variable and the
                            // start value is hardcoded to zero.
                            float percentToDim = v.getTranslationX() / dimmWidth;

                            // Do not perform dimming when dragging the
                            // top view above horizontal limit
                            if(percentToDim <= 1.0F)
                            {
                                dimmedView.setAlpha(1.0F - percentToDim);
                            }

//                            return true;
                            break;

                        case MotionEvent.ACTION_POINTER_UP:

                            int currentId = ev.getPointerId(ev.getActionIndex());

                            // If any of the initial two fingers are lifted up, animate the view
                            if(currentId == pointerDownId || currentId == downId)
                            {
                                animating = true;
                                animateView(v);
                            }

//                            return true;
                            break;

                        case MotionEvent.ACTION_UP:

                            downId = INVALID_POINTER;
                            pointerDownId = INVALID_POINTER;

                            /*
                            *   By design, if any of the initial two fingers that started the
                            *   drag motion get lifted up, animate the top view to its final position.
                            *   Sometimes ACTION_POINTER_UP is not reported to this OnTouchListener
                            *   and the animation hangs in place. Since ACTION_UP is always triggered,
                            *   force the animation to finish.
                            */

                            if(!animating)
                            {
                                animateView(v);
                            }

//                            return true;
                            break;
                    }

                    return true;
                }
            };
        }

        return topChildTouchListener;
    }

    private ObjectAnimator getDimmer()
    {
        if(dimmer == null)
        {
            dimmer = new ObjectAnimator();
            dimmer.setDuration(ANIMATOR_DURATION);
            dimmer.setProperty(View.ALPHA);
            dimmer.setInterpolator(ANIMATOR_INTERPOLATOR);
        }

        return dimmer;
    }

    private ObjectAnimatorPostman getPostman()
    {
        if(animator ==  null)
        {
            animator = new ObjectAnimatorPostman()
            {
                @Override
                public void animationEnded()
                {
                    if(dirty)
                    {
                        dirty = false;

                        //handle left to right scenario
                        if(currentOrigin == AnimationOrigin.FROM_LEFT)
                        {
                            upX = animationEndValueToRight;
                        }

                        if(currentOrigin == AnimationOrigin.FROM_RIGHT)
                        {
                            upX = ANIMATION_END_VALUE_TO_LEFT;
                        }

                        currentOrigin = currentOrigin == AnimationOrigin.FROM_LEFT ?
                                AnimationOrigin.FROM_RIGHT : AnimationOrigin.FROM_LEFT;
                    }
                    else
                    {
                        //animation started from LEFT and snapped back to LEFT side
                        if(currentOrigin == AnimationOrigin.FROM_LEFT)
                        {
                            upX = ANIMATION_END_VALUE_TO_LEFT;
                        }
                        //animation started from RIGHT and snapped back to RIGHT side
                        else
                        {
                            upX = animationEndValueToRight;
                        }
                    }

                    //notify the hosting activity which child is visible
                    if(hostActivity != null)
                    {
                        if(currentOrigin == AnimationOrigin.FROM_RIGHT)
                        {
                            hostActivity.whichOneIsVisible(VisibleChild.BOTTOM);
                        }
                        else
                        {
                            hostActivity.whichOneIsVisible(VisibleChild.TOP);
                        }
                    }

                    dragging = false;
                    downId = INVALID_POINTER;
                    pointerDownId = INVALID_POINTER;
                    animating = false;
                }
            };

            animator.setProperty(View.TRANSLATION_X);
            animator.setInterpolator(ANIMATOR_INTERPOLATOR);
            animator.setDuration(ANIMATOR_DURATION);
        }

        return animator;
    }

    private void animateView(View target)
    {
        upX = target.getTranslationX();

        animator.setTarget(target);

        // If we are on the left side and we have to animate to the right side.
        // Then we need to pass the 25% mark which is thresholdToRight and we should animate to the
        // 95% of screen width. Otherwise, flip the values to indicate right to left animation
        float currentThreshold;
        float currentEndValue;

        if(currentOrigin == AnimationOrigin.FROM_LEFT)
        {
            currentThreshold = thresholdToRight;
            currentEndValue = animationEndValueToRight;
        }
        else
        {
            currentThreshold = thresholdToLeft;
            currentEndValue = ANIMATION_END_VALUE_TO_LEFT;
        }

        if(currentOrigin == AnimationOrigin.FROM_LEFT)
        {
            if(upX >= currentThreshold)
            {
                dirty = true;
                animator.setFloatValues(currentEndValue);
            }
            else
            {
                dirty = false;

                animator.setFloatValues(ANIMATION_END_VALUE_TO_LEFT);
            }
        }
        else
        {
            if(upX <= currentThreshold)
            {
                dirty = true;
                animator.setFloatValues(currentEndValue);
            }
            else
            {
                dirty = false;
                animator.setFloatValues(animationEndValueToRight);
            }
        }

        // Set up dimmer animator
        float currentTrans = target.getTranslationX();

        // Left to right scenario
        float startingAlpha = 1.0F - (currentTrans / dimmWidth);
        float endAlpha = 0.0F;

        // Left to right scenario
        if(currentOrigin == AnimationOrigin.FROM_LEFT)
        {
            // Animating fully to the right
            if(upX >= currentThreshold)
            {
                dirty = true;
                endAlpha = 0.0F;
            }
            // Snapping back to the left screen edge
            else
            {
                dirty = false;
                endAlpha = 1.0F;
            }
        }
        if(currentOrigin == AnimationOrigin.FROM_RIGHT)
        {
            // We have to animate
            if(upX <= currentThreshold)
            {
                dirty = true;
                endAlpha = 1.0F;
            }
            // Snapping back to the right screen edge
            else
            {
                dirty = false;
                // Avoid negative starting alphas
                startingAlpha = Math.abs(startingAlpha);
                endAlpha = 0.0F;
            }
        }

        dimmer.setFloatValues(startingAlpha, endAlpha);
        dimmer.setTarget(dimmedView);
        dimmer.start();
        animator.start();
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        /** Handle XML defined children here */
        if(this.getChildCount() > 3)
        {
            throw new RuntimeException(this.getClass().getSimpleName() +
                    " does not support XML defined children. Add children via bottomChild and topChild attributes");
        }

        this.getChildAt(0).setOnTouchListener(getBottomChildTouchListener());
        this.dimmedView = findViewById(R.id.dimmed_view);
        this.getChildAt(2).setOnTouchListener(getTopChildTouchListener());

        dimmer = getDimmer();
    }

    public void revealBottomChild()
    {
        //don't allow touch events
        dirty = true;
        animating = true;
        animator.setFloatValues(animationEndValueToRight);
        animator.start();
    }

    public void hideBottomChild()
    {
        //don't allow touch events
        dirty = true;
        animating = true;
        animator.setFloatValues(ANIMATION_END_VALUE_TO_LEFT);
        animator.start();
    }
}
