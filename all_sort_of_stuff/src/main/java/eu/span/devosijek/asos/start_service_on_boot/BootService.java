package eu.span.devosijek.asos.start_service_on_boot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BootService extends Service
{
    final String tag = "AllSortOfStuff";

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(tag, "onStartCommand");

        Receiver.completeWakefulIntent(intent);
        return Service.START_STICKY;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(tag, "onCreate" );
    }


}
