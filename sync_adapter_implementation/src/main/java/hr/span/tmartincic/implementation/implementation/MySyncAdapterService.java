package hr.span.tmartincic.implementation.implementation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MySyncAdapterService extends Service
{
    private static final Object adapterLock = new Object();
    //adapter lives in the context of the service - aka lives as the running service
    private static MySyncAdapter mySyncAdapter = null;

    //instantiate our sync adapter upon creation
    @Override
    public void onCreate()
    {
        synchronized (adapterLock)
        {
            if(mySyncAdapter == null)
            {
                mySyncAdapter = new MySyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mySyncAdapter.getSyncAdapterBinder();
    }
}
