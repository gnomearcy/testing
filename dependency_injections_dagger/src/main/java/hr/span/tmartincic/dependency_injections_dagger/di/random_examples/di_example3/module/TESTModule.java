package eu.span.dev.osijek.di.compdepend.module;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.Motor;
import eu.span.dev.osijek.di.compdepend.model.Vehicle;

@Singleton
@Module
public class TESTModule
{
    @Provides
    Motor provideMotor()
    {
        return new Motor();
    }

    @Singleton
    @Provides
    Vehicle providesVehicle(Motor motor)
    {
        return new Vehicle(motor);
    }

    @Singleton
    @Provides
    SharedPreferences evoDamTiSharedPrefs(String key, Activity activity)
    {
        return activity.getSharedPreferences(key, Context.MODE_PRIVATE);
    }
}
