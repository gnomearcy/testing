package hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.Consts;

@Module
public class Module1
{
    public static String whichCalled;

    @Singleton
    @Named(Consts.SINGLETON_NO_DEPS)
    @Provides
    public Module1Object provideModule1Object()
    {
        whichCalled = "singleton_no_deps";
        return new Module1Object();
    }

    @Singleton
    @Named(Consts.NON_SINGLETON_NO_DEPS)
    @Provides
    public Module1Object provideModule1Object1()
    {
        whichCalled = "non-singleton-no-deps";
        return new Module1Object();
    }

    public static class Module1Object
    {
        public int value;
        public String internalString;

        @Inject
        public Module1Object()
        {
            internalString = whichCalled;
            value = new Random().nextInt(100000);
        }
    }
}
