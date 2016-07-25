package eu.span.devosijek.generics.test3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DummyUtil
{
    public static boolean isConnectingToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if(info != null)
            {
                for(NetworkInfo networkInfo : info)
                {
                    if(networkInfo.isConnectedOrConnecting())
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
