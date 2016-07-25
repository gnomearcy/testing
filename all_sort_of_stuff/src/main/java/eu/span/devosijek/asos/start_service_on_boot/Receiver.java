package eu.span.devosijek.asos.start_service_on_boot;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class Receiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
         if(intent != null)
         {
             if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
             {
                 Intent serviceIntent = new Intent();
                 serviceIntent.setClass(context, BootService.class);
                 startWakefulService(context, serviceIntent);
             }
         }
    }
}
