package eu.span.dev.osijek.di.compdepend.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleApplication
{
    private Application application;

    public ModuleApplication(Application application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication()
    {
        return this.application;
    }
}
