package hr.span.tmartincic.couchgcmsync;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Explicitly state the handler of the message
//        ComponentName gcmHandler = new ComponentName(context, GcmMessageHandler.class);
        ComponentName gcmHandler = new ComponentName(context.getPackageName(), GcmMessageHandler.class.getName());

        //sets the component that will handle this intent
        intent.setComponent(gcmHandler);

        //Start the service handler
        startWakefulService(context, intent);
        setResultCode(Activity.RESULT_OK);
    }
}
