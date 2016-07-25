package hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

@Module
public class Module2
{
    public static String whichMethodCalled;

    @Provides
    public Module2Object getTheWrapper()
    {
        whichMethodCalled = "Module2 - getting the wrapper.";
        return new Module2Object();
    }

    // This object wraps a Provider for Module1Object which Module3Object can use to retrieve
    // instances of Modul1Object
    public static class Module2Object
    {
        public int value;
        public String internalString;

        @Inject
        public Provider<Module1.Module1Object> dependencyProvider;

//        @Inject
        public Module2Object()
        {
            internalString = whichMethodCalled;
            value = new Random().nextInt(100000);
//            dependencyProvider = null;
        }
    }
}
