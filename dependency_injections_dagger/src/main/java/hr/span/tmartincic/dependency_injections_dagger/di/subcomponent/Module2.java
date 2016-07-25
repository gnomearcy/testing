package hr.span.tmartincic.dependency_injections_dagger.di.subcomponent;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class Module2
{
//    @Provides
//    @Named("nondependant")
//    public Module2Object getModule2Object()
//    {
//        return new Module2Object();
//    }

    @Provides
    @Named("dependant")
    public Module2Object getModule2Object(Module1.Module1Object dependency)
    {
        // Dependency is here just to have a wrapper, but it's not used.
        return new Module2Object();
    }

    public static class Module2Object
    {
        public int value;

        @Inject
        public Module2Object()
        {
            value = new Random().nextInt(123233);
        }
    }
}
