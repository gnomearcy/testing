package hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.ActChainDependency;

@Comp3Scope
@Component(modules = Module3.class, dependencies = Comp2.class)
public interface Comp3
{
    Module3.Module3Object compModule3Object();
//    void inject(ActChainDependency activity);
}
