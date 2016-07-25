package eu.span.dev.osijek.dimvp.demo.components;

import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Component;
import eu.span.dev.osijek.dimvp.demo.module.ModuleApplication;
import eu.span.dev.osijek.dimvp.demo.module.ModuleServices;

@Singleton
@Component(modules = {ModuleServices.class, ModuleApplication.class})
public interface ComponentServices
{
        // Publicly exposed types from ModuleServices
        // They all require Application object, which is found in ModuleApplication
        // The user of this component can only Inject below exposed types
        SharedPreferences getPrefs();
        WifiManager getManager();
        LayoutInflater getInflater();
}
