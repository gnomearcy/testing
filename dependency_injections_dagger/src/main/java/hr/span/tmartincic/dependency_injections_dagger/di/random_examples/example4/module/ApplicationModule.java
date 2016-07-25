package eu.span.dev.osijek.testdi.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule
{
    private final Application application;

    public ApplicationModule(Application application)
    {
        this.application = application;
    }

    @Provides
    Application getApplication()
    {
        return this.application;
    }
}
