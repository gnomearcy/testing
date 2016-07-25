package hr.span.tmartincic.implementation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyAuthenticatorService extends Service
{
    @Override
    public IBinder onBind(Intent intent)
    {
        //onbound service that runs as long as it's not explicitly killed
        MyAuthenticator myAuthenticator = new MyAuthenticator(this);
        return myAuthenticator.getIBinder();
    }
}
