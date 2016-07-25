package hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = Module1.class)
public interface Comp1
{
    // Link to subcomponent
    Module1.Module1Object exposeObjectFromModule1();
    Comp2 plus(Module2 module);
}
