package eu.span.devosijek.generics.test3;

import android.content.Context;

/**
 *  We have ActivityBase and FragmentBase.
 *  Each of them should have access to Utils' class methods
 *  To avoid repeating same code in both ActivityBase and FragmentBase
 *  we need to find a way to provide implementation of those methods in an Interface which can be
 *  implemented by both ActivityBase and FragmentBase classes. That way we can maintain the
 *  code at one place.
 */

/**
 *  "T" => implementing type, either an Activity or Fragment
 */
public interface InterfaceImpl
{
    enum Methods
    {
        IS_CONNECTING_TO_INTERNET
        {
            @Override
            public boolean isConnectingToInternet(Context ctx)
            {
                return false;
            }

        };

        public abstract boolean isConnectingToInternet(Context ctx);
    }

    /**
     * Extension methods are not supported at this language level (Java 8 brings static methods to interfaces)
     */
}
