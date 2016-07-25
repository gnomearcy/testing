package eu.span.devosijek.generics.test3;

import android.content.Context;

public enum EnumMethods
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
