package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.scoping.data.utils.AnalyticsManager;
import eu.span.dev.osijek.di.scoping.data.utils.Validator;

// All provided objects are singletons
@Module
public class ModuleApplication
{
    private Application application;

    public ModuleApplication(Application application)
    {
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideApplication()
    {
        return application;
    }

    @Provides
    @Singleton
    AnalyticsManager provideAnalyticsManager()
    {
        return new AnalyticsManager(application);
    }

    @Singleton
    @Provides
    Validator provideValidator()
    {
        return new Validator();
    }

    @Provides
    @Singleton
    HeavyExternalLibrary provideHeavyExternalLibrary()
    {
        HeavyExternalLibrary heavyExternalLibrary = new HeavyExternalLibrary();
        heavyExternalLibrary.init();
        return heavyExternalLibrary;
    }

    @Singleton
    @Provides
    HeavyLibraryWrapper provideHeavyLibraryWrapper()
    {
        return new HeavyLibraryWrapper();
    }
}
