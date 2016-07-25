package hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

@Module
public class Module3
{
    public static String whichMethodCalled;

    @SingletonComp3
    @Provides
    public Module3Object giveSingletonWithDifferentDependencies(Module2.Module2Object dependencyWithProviderWrapper)
    {
        whichMethodCalled = "Creating object3 with wrapper of type object2 for instances of object1";
        return new Module3Object(dependencyWithProviderWrapper);
    }

    // I want this object to be singleton with different instances of Module1Object
    public static class Module3Object
    {
        public int value;
        public String internalString;
        public Provider<Module1.Module1Object> provider;

//        @Inject
//        public Module3Object()
//        {
//            internalString = "Injecting from Module3Object constructor.";
//            value = new Random().nextInt(100000);
//            provider = null;
//        }

        public Module3Object(Module2.Module2Object dependencyWithProviderWrapper)
        {
            internalString = "Injecting from constructor with dependency.";
            provider = dependencyWithProviderWrapper.dependencyProvider;
        }

        public Module1.Module1Object getObject1()
        {
            return provider.get();
        }
    }
}
