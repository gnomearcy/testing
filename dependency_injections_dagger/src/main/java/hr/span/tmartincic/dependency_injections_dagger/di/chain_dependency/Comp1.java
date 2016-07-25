package hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = Module1.class)
public interface Comp1
{
    Module1.Module1Object compModule1Object();
}
