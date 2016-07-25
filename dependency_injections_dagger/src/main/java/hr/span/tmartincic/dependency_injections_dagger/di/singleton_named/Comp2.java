package hr.span.tmartincic.dependency_injections_dagger.di.singleton_named;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.ActSingletonNamedObjects;

@SingletonComp2
@Component(modules = Module2.class, dependencies = Comp1.class)
public interface Comp2
{
    void inject(ActSingletonNamedObjects act);
}
