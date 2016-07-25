package hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances;

import dagger.Subcomponent;
import hr.span.tmartincic.dependency_injections_dagger.ActSingletonDifferentInstances;

@Subcomponent(modules = Module2.class)
public interface Comp2
{
    Module2.Module2Object exposeObject2();

    void inject(ActSingletonDifferentInstances target);
}
