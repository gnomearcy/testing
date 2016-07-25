package hr.span.tmartincic.dependency_injections_dagger.di.subcomponent;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class Module1
{
    @Singleton
    @Provides
    public Module1Object getModule1Object()
    {
        return new Module1Object();
    }

    public static class Module1Object
    {
        public int value;

        @Inject
        public Module1Object()
        {
            value = new Random().nextInt(100000);
        }
    }
}
