package hr.span.tmartincic.dependency_injections_dagger.di.singleton_named;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.ActSingletonNamedObjects;

@Module
public class Module1
{
    public static String whichMethodCalled;

    // @Named annotation must be here - otherwise multiple binding compile error
    @Named(ActSingletonNamedObjects.Constants.NO_SINGLETON_NO_DEPS)
    @Provides
    public Module1Object getDifferentWithNoDeps()
    {
        whichMethodCalled = "Non singleton with no dependencies";
        return new Module1Object();
    }

    @Singleton
    @Named(ActSingletonNamedObjects.Constants.SINGLETON_NO_DEPS)
    @Provides
    public Module1Object getSingletonWithNoDeps()
    {
        whichMethodCalled = "Singleton with no dependencies";
        return new Module1Object();
    }

    @Provides
    public Module1Object getDefault()
    {
        whichMethodCalled = "Default non named @Provides method.";
        return new Module1Object();
    }

    public static class Module1Object
    {
        public int value;
        public String internalString;

        @Inject
        public Module1Object()
        {
            internalString = whichMethodCalled;
            value = new Random().nextInt(100000);
        }
    }
}
