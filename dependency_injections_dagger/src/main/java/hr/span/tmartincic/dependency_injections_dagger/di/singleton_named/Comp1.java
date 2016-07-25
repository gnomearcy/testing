package hr.span.tmartincic.dependency_injections_dagger.di.singleton_named;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.ActSingletonNamedObjects;

@Singleton
@Component(modules = Module1.class)
public interface Comp1
{
    @Named(ActSingletonNamedObjects.Constants.NO_SINGLETON_NO_DEPS)
    Module1.Module1Object getIt();

    @Named(ActSingletonNamedObjects.Constants.SINGLETON_NO_DEPS)
    Module1.Module1Object getIt2();

    // This is how we expose the default @Provides method for the dependent components
    Module1.Module1Object getDefaultObject();
}
