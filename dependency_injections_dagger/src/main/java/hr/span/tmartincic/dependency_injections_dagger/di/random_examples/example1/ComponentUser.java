package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1;

import dagger.Component;
import eu.span.dev.osijek.di.scoping.data.api.ModuleUser;
import eu.span.dev.osijek.di.scoping.data.ui.module.ModuleActivityRepositoriesList;

@UserScope
@Component(modules = ModuleUser.class)
public interface ComponentUser
{
    // Subcomponent approach - sub component inherits everything from its' parent object graph
    RepositoriesListActivityComponent plus(ModuleActivityRepositoriesList module);
    RepositoryDetailsActivityComponent plus(ModuleActivityRepositoryDetails module);
}
