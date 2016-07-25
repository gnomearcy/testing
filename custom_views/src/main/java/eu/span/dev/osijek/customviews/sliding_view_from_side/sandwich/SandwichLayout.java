package eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import eu.span.dev.osijek.customviews.R;


/**
 *  Wrapper ViewGroup layout with following properties:
 *
 *  - hosts two ViewGroup children
 *  - children are stacked on top of each other
 *  - top child features two finger horizontal drag, simulating the revealing of the bottom child
 *
 *  of this ViewGroup which have to point to a valid layout XML file. A valid layout contains a ViewGroup
 *  root with {@link android.R.attr#layout_width} and {@link android.R.attr#layout_height} attributes
 *  set to match_parent.
 *
 *  This view requires both {@link android.R.attr#layout_width} and {@link android.R.attr#layout_height}
 *  with value match_parent. Also, explicit XML defined children of this ViewGroup cause a RuntimeException.
 */
public class SandwichLayout extends RelativeLayout
{
    private static final String tag = "CustomViews";

    /** Global context */
    private final ViewGroup me;
    private OnTouchListener bottomChildTouchListener;
    private OnTouchListener topChildTouchListener;
    private ObjectAnimatorPostman animator;


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

    /**
     *  are not specified in the layout file of this view. For undefined values of these
     *  attributes, a RuntimeException is thrown.
     */
    private static final int UNDEFINED_CHILD = -1;

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
     *  Usually holds the value of 0.0F;
     *  Considered to be the starting position of the top child.
     */
    private float animationEndValueToLeft;

    /**
     *  Constant used to tweak the {@link SandwichLayout#animationEndValueToLeft}
     */
    private static final float animationStartOffsetPercentage = 0.0F;

    /**
     *  Constant used to tweak the {@link SandwichLayout#animationEndValueToRight}
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

    private int horizontalThreshold;
    private static final float enableDrag = 0.3F;

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

    public SandwichLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        me = this;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SandwichLayout);
        int resourceIdBottomChild = array.getResourceId(R.styleable.SandwichLayout_bottomChild, UNDEFINED_CHILD);
        int resourceIdTopChild = array.getResourceId(R.styleable.SandwichLayout_topChild, UNDEFINED_CHILD);
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

        /** Handle undefined children of this view */
        if(resourceIdBottomChild == UNDEFINED_CHILD || resourceIdTopChild == UNDEFINED_CHILD)
        {
            throw new RuntimeException(this.getClass().getSimpleName() +
                    " requires both bottomChild and topChild attributes to " +
                    "point to a valid layout resource");
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View inflatedBottomChild = null;
        View inflatedTopChild = null;

        try
        {
            inflatedBottomChild = inflater.inflate(resourceIdBottomChild, this, false);
            inflatedTopChild = inflater.inflate(resourceIdTopChild, this, false);
        }
        catch(Exception e)
        {
            //In case the supplied resource file is not a layout file, inflater will throw an exception
            //Eat the exception here and let the next if-check throw an exception with proper description
        }

        /** Handle non-ViewGroup roots of supplied XML layouts */
        if(!(inflatedBottomChild instanceof ViewGroup))
        {
            throw new RuntimeException("Root of the layout specified in attribute bottomChild is not a ViewGroup type or" +
                    " the supplied resource is not a layout resource.");
        }
        if(!(inflatedTopChild instanceof ViewGroup))
        {
            throw new RuntimeException("Root of the layout specified in attribute topChild is not a ViewGroup type or" +
                    " the supplied resource is not a layout resource.");
        }

        /** Handle children dimension attributes mismatch */
        ViewGroup.LayoutParams parametersBottomChild = inflatedBottomChild.getLayoutParams();
        ViewGroup.LayoutParams parametersTopChild = inflatedTopChild.getLayoutParams();

        /** Handle childrens' dimension attributes mismatch */
        if (
            parametersBottomChild.width     != MATCH_PARENT ||
            parametersBottomChild.height    != MATCH_PARENT ||
            parametersTopChild.width        != MATCH_PARENT ||
            parametersTopChild.height       != MATCH_PARENT
           )
        {
            throw new RuntimeException("Both root ViewGroups specified in bottomChild and topChild " +
                    "attributes need to have their android:layout_width and android:layout:height " +
                    "attributes set to match_parent.");
        }

        initialize(context);
        array.recycle();
    }

    private void initialize(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float screenX = dm.widthPixels;
        thresholdToRight = percentage * screenX;
        thresholdToLeft = ( 1.0F - percentage ) * screenX;
        animationEndValueToRight = screenX * ( 1.0F - animationEndOffsetPercentage );
        animationEndValueToLeft = screenX * animationStartOffsetPercentage;
        animator = getPostman();
        currentOrigin = AnimationOrigin.FROM_LEFT;

        horizontalThreshold = (int) (screenX * enableDrag);
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
                    return me.getChildAt(1).dispatchTouchEvent(ev);
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
                                return true;

                            if(ev.getRawX() >= horizontalThreshold)
                                return false;
//                            return true;
                            break;

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
//                            return true;
                            break;

                        case MotionEvent.ACTION_POINTER_UP:

                            int currentId = ev.getPointerId(ev.getActionIndex());

                            //If any of the initial two fingers are lifted up, animate the view
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

                            if(!animating)
                            {
                                animateView(v);
                            }

//                            return true;
                            break;
                    }

                    requestLayout();
                    return true;
                }
            };
        }

        return topChildTouchListener;
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
                            upX = animationEndValueToLeft;
                        }

                        currentOrigin = currentOrigin == AnimationOrigin.FROM_LEFT ?
                                AnimationOrigin.FROM_RIGHT : AnimationOrigin.FROM_LEFT;
                    }
                    else
                    {
                        //animation started from LEFT and snapped back to LEFT side
                        if(currentOrigin == AnimationOrigin.FROM_LEFT)
                        {
                            upX = animationEndValueToLeft;
                        }
                        //animation started from RIGHT and snapped back to RIGHT side
                        else
                        {
                            upX = animationEndValueToRight;
                        }
                    }

                    //notify the hosting activity which child is visible
                    if(currentOrigin == AnimationOrigin.FROM_RIGHT)
                    {
                        hostActivity.whichOneIsVisible(VisibleChild.BOTTOM);
                    }
                    else
                    {
                        hostActivity.whichOneIsVisible(VisibleChild.TOP);
                    }

                    dragging = false;
                    downId = INVALID_POINTER;
                    pointerDownId = INVALID_POINTER;
                    animating = false;
                }

                @Override
                public void onAnimationProgress(int percentage)
                {
                    //todo implement dimming of the bottom view
                }
            };

            animator.setPropertyName("translationX");
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(250);
        }

        return animator;
    }

    private void animateView(View target)
    {
        upX = target.getTranslationX();

        animator.setTarget(target);

        //if we are on the left side and we have to animate to the right side
        //then we need to pass the 25% mark which is thresholdToRight and we should animate to the 95% of screen width
        //otherwise, flip the values to indicate right to left animation
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
            currentEndValue = animationEndValueToLeft;
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
                animator.setFloatValues(0.0F);
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

        animator.start();
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        /** Handle XML defined children here */
        if(this.getChildCount() > 2)
        {
            throw new RuntimeException(this.getClass().getSimpleName() +
                    " does not support XML defined children. Add children via bottomChild and topChild attributes");
        }

        this.getChildAt(0).setOnTouchListener(getBottomChildTouchListener());
        this.getChildAt(1).setOnTouchListener(getTopChildTouchListener());
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
        animator.setFloatValues(animationEndValueToLeft);
        animator.start();
    }
}
