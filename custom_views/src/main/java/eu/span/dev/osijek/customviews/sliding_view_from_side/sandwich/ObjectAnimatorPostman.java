package eu.span.dev.osijek.customviews.sliding_view_from_side.sandwich;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Handler;
import android.os.Message;
import android.util.Property;

import java.lang.ref.WeakReference;

/**
 *  Wrapped class around ObjectAnimator which provides a callback
 *  for animation end and animation current progress states.
 */
public abstract class ObjectAnimatorPostman
{
    private ObjectAnimator objectAnimator;
    private Dispatcher dispatcher;

    public ObjectAnimatorPostman()
    {
        this.objectAnimator = new ObjectAnimator();
        this.dispatcher = new Dispatcher(this);
    }

    public abstract void animationEnded();

    public ObjectAnimator getObjectAnimator()
    {
        return objectAnimator;
    }

    /** Delegate methods */
    public void start()
    {
        Message msg = new Message();
        msg.what = Dispatcher.ANIMATION_END;

        if(!objectAnimator.isStarted())
        {
            objectAnimator.start();
        }
        dispatcher.sendMessageDelayed(msg, objectAnimator.getDuration());
    }

    public void setDuration(long duration)
    {
        objectAnimator.setDuration(duration);
    }

    public void setFloatValues(float...values)
    {
        objectAnimator.setFloatValues(values);
    }

    public void setInterpolator(TimeInterpolator interpolator)
    {
        objectAnimator.setInterpolator(interpolator);
    }

    public void setTarget(Object target)
    {
        objectAnimator.setTarget(target);
    }

    public void setPropertyName(String propertyName)
    {
        objectAnimator.setPropertyName(propertyName);
    }

    public void setProperty(Property property)
    {
        objectAnimator.setProperty(property);
    }

    /** Private message dispatcher */
    private static final class Dispatcher extends Handler
    {
        WeakReference<ObjectAnimatorPostman> enclosingClass;
        public static final int ANIMATION_END = 123;

        public Dispatcher(ObjectAnimatorPostman animator)
        {
            this.enclosingClass = new WeakReference<>(animator);
        }

        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case ANIMATION_END:
                    if(enclosingClass != null)
                    {
                        enclosingClass.get().animationEnded();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
