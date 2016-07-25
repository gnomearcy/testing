package hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.ActSingletonDifferentInstances;

@SingletonComp3
@Component(modules = Module3.class, dependencies = Comp2.class)
public interface Comp3
{
    void inject(ActSingletonDifferentInstances target);
}
