package eu.span.dev.osijek.di.compdepend.module;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModulePrefs
{
    private String kljuc;

    public ModulePrefs(String kljuc)
    {
        this.kljuc = kljuc;
    }

    @Provides
    String dajKljuc()
    {
        return this.kljuc;
    }

    @Singleton
    @Provides
    SharedPreferences dajMiPrefs(String kljuc, Activity activity)
    {
        return activity.getSharedPreferences(kljuc, Context.MODE_PRIVATE );
    }
}
