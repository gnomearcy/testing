package eu.span.dev.osijek.dimvp.demo.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleServices
{
    private final String prefs = "Preferences_123123";

    // We require an application to get the system service
    @Singleton
    @Provides
    LayoutInflater provideLayoutInflater(Application application)
    {
        return (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
//
    @Singleton
    @Provides
    WifiManager provideWifiManager(Application application)
    {
        return (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Application app)
    {
        return app.getSharedPreferences(prefs, Context.MODE_PRIVATE);
    }
}
