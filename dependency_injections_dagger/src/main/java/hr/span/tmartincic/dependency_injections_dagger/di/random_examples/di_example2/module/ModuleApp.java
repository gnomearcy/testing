package eu.span.dev.osijek.di.compdepend.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleApp
{
    private Application application;

    public ModuleApp(Application app)
    {
        this.application = app;
    }

    // Return Application object as singleton
    @Singleton
    @Provides
    Application provideApp()
    {
        return this.application;
    }
}
