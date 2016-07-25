package eu.span.dev.osijek.di.compdepend.module;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3.TESTModel;
import eu.span.dev.osijek.di.compdepend.model.Motor;
import eu.span.dev.osijek.di.compdepend.model.Vehicle;

@Singleton
@Module
public class TESTModuleSingletonWithSingletonMethods
{
    @Singleton
    @Provides
    WifiManager provideWifi(Application app)
    {
        return (WifiManager) app.getSystemService(Context.WIFI_SERVICE);
    }

    @Singleton
    @Provides
    Motor provideMotor()
    {
        return new Motor();
    }

    @Singleton
    @Provides
    TESTModel dajMiTestModel(Application app, Context ctx, Vehicle v, Motor m)
    {
        return new TESTModel(app, ctx, v, m);
    }
}
