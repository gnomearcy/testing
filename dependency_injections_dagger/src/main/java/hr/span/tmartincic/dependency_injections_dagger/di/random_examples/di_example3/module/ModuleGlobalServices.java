package eu.span.dev.osijek.di.compdepend.module;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleGlobalServices
{
    private final String prefs = "Preferences_123123";

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Activity activity)
    {
        return activity.getSharedPreferences(prefs, Context.MODE_PRIVATE);
    }
}
