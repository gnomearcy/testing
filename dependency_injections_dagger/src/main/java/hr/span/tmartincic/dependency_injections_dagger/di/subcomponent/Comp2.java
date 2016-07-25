package hr.span.tmartincic.dependency_injections_dagger.di.subcomponent;

import dagger.Subcomponent;
import hr.span.tmartincic.dependency_injections_dagger.ActSubcomponent;

@Subcomponent(modules = Module2.class)
public interface Comp2
{
    Module2.Module2Object getModule2Object();
    void inject(ActSubcomponent activity);
}
