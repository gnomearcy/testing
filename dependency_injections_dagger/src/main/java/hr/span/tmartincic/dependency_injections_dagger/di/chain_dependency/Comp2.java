package hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.ActChainDependency;

@Comp2Scope // required since this component depends on Comp1 which is a singleton
@Component(modules = Module2.class, dependencies = Comp1.class)
public interface Comp2
{
    Module2.Module2Object compModule2Object();
    void inject(ActChainDependency activity);
}
