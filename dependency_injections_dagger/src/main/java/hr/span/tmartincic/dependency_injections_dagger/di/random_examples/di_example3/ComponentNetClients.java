package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import dagger.Component;
import eu.span.dev.osijek.di.compdepend.module.ModuleGoogle;
import eu.span.dev.osijek.di.compdepend.module.ModuleYahoo;

@Component(modules = {ModuleGoogle.class, ModuleYahoo.class})
public interface ComponentNetClients
{
    // TODO Uncomment to
    void injectIntoTestDi(TestDi act);
}
