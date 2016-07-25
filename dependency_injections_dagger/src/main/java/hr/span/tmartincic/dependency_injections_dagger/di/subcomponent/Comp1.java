package hr.span.tmartincic.dependency_injections_dagger.di.subcomponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = Module1.class)
public interface Comp1
{
    // Get an object from this component's module
    Module1.Module1Object get();
    // Declare Comp2 as subcomponent of this component
    Comp2 subcomponentDeclaration(Module2 subcompModule);
}
