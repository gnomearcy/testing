package hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances;

import java.util.Random;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class Module1
{
    public static String whichMethodCalled;

    @Provides
    public Module1Object provideObject1()
    {
        whichMethodCalled = "Default provide method for Module1Object.";
        return new Module1Object();
    }

    // I want this to be injected into Module2Object into a Provider which Module3Object can use
    // to access Module1Objects, but to remain a singleton.
    public static class Module1Object
    {
        public int value;
        public String internalString;

//        @Inject
        public Module1Object()
        {
            internalString = whichMethodCalled;
            value = new Random().nextInt(100000);
        }
    }
}
