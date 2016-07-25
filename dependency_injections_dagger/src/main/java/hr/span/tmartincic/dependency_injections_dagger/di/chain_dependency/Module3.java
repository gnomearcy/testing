package hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.Consts;

@Module
public class Module3
{
    // Give me "singleton" instances of Module3Object.
    // To get this object anywhere, annotate the field with @Inject and @Named("nodeps")
    @Provides
    @Comp3Scope
    @Named(Consts.SINGLETON_NO_DEPS)
    public Module3Object getSingletonNoDeps()
    {
        return new Module3Object();
    }

    // Give me singleton instances of Module3Object with a wrapper
    // Annotate the field with @Inject and @Named("deps")
    @Provides
    @Comp3Scope
    @Named(Consts.SINGLETON_DEPS)
    public Module3Object getSingletonDeps(Module2.Module2Object dependency)
    {
        return new Module3Object(dependency);
    }

    @Provides
    @Named(Consts.NON_SINGLETON_DEPS)
    public Module3Object getNonSingletonDeps(Module2.Module2Object dependency)
    {
        return new Module3Object(dependency);
    }

    @Provides
    @Named(Consts.NON_SINGLETON_NO_DEPS)
    public Module3Object getNonSingletonNoDeps()
    {
        return new Module3Object();
    }

    public static class Module3Object
    {
        public int value;
        public Module2.Module2Object dependency;
        public String constructor;

        @Inject
        public Module3Object()
        {
            value = new Random().nextInt(999982);
            dependency = null;
            constructor = "no param";
        }

        public Module3Object(Module2.Module2Object dependency)
        {
            value = new Random().nextInt(212312);
            this.dependency = dependency;
            constructor = "param";
        }
    }
}
