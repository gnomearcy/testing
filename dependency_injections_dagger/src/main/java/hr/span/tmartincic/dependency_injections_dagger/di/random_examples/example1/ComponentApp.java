package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1;

import javax.inject.Singleton;

import dagger.Component;
import eu.span.dev.osijek.di.scoping.data.api.ModuleGithubApi;
import eu.span.dev.osijek.di.scoping.data.api.ModuleUser;
import eu.span.dev.osijek.di.scoping.data.ui.component.ComponentActivitySplash;
import eu.span.dev.osijek.di.scoping.data.ui.module.ModuleActivitySplash;

@Singleton
@Component(modules = {ModuleApplication.class, ModuleGithubApi.class})
public interface ComponentApp
{
    ComponentActivitySplash plus(ModuleActivitySplash module);
    ComponentUser plus(ModuleUser moduleUser);
}
