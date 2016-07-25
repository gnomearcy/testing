package eu.span.dev.osijek.dimvp.demo.module;

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
    Application provideApplication()
    {
        return this.application;
    }
}
