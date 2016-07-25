package hr.span.tmartincic.dependency_injections_dagger.di.singleton_named;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Scope;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.ActSingletonNamedObjects;

@Module
public class Module2
{
    public static String whichMethodCalled;

    /**
     *  It's impossible to have a singleton object with different dependencies whenever we inject
     *  everything into a Target
     */
    @SingletonComp2
    @Named(ActSingletonNamedObjects.Constants.SINGLETON_DIFFERENT_DEPS)
    @Provides
    public Module2Object getSingletonWithDifferentDeps(Module1.Module1Object dependency)
    {
        whichMethodCalled = "Singleton with wrapper.";
        return new Module2Object(dependency);
    }

    @Named(ActSingletonNamedObjects.Constants.NO_SINGLETON_DIFFERENT_DEPS)
    @Provides
    public Module2Object getDifferentWithDifferentDeps(Module1.Module1Object dependency)
    {
        whichMethodCalled = "Non singleton with wrapper.";
        return new Module2Object(dependency);
    }

    @Named(ActSingletonNamedObjects.Constants.NO_SINGLETON_SINGLETON_DEPS)
    @Provides
    public Module2Object getDifferentWithSingletonDeps(Module1.Module1Object singletonDependency)
    {
        whichMethodCalled = "Non singleton with singleton wrapper.";
        return new Module2Object(singletonDependency);
    }

    @SingletonComp2
    @Named(ActSingletonNamedObjects.Constants.SINGLETON_SINGLETON_DEPS)
    @Provides
    public Module2Object getSingletonWithSingletonDeps(Module1.Module1Object singletonDependency)
    {
        whichMethodCalled = "Singleton with singleton wrapper.";
        return new Module2Object(singletonDependency);
    }

    public static class Module2Object
    {
        public int value;
        public String internalString;
        public Module1.Module1Object dependency;

        @Inject
        public Module2Object()
        {
            internalString = whichMethodCalled;
            value = new Random().nextInt(100000);
            dependency = null;
        }

        public Module2Object(Module1.Module1Object dependency)
        {
            internalString = whichMethodCalled;
            value = new Random().nextInt(100000);
            this.dependency = dependency;
        }
    }
}
