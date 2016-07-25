package hr.span.tmartincic.ews;

import android.app.Application;
import android.util.Log;

public class MyApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.d("ksoap2", "Initializing application class");

        //Tried to configure NTLM authentication with jcifs library
        //Source: exchange bookmark folder (negdje je..)
        jcifs.Config.registerSmbURLHandler();
        jcifs.Config.setProperty("jcifs.smb.client.domain", ServiceInfo.userDomain);
        jcifs.Config.setProperty("jcifs.smb.client.username", ServiceInfo.username);
        jcifs.Config.setProperty("jcifs.smb.client.password", ServiceInfo.password);
//        jcifs.Config.setProperty("jcifs.netbios.hostname", client's netbios hostname');

    }
}
