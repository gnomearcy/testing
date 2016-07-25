package eu.span.dev.osijek.di.compdepend.component;

import dagger.Component;
import eu.span.dev.osijek.di.compdepend.module.ModuleApp;
import eu.span.dev.osijek.di.compdepend.module.ModuleUser;

public class Components
{
    // Component dependency through subcomponents
    @Component(modules = ModuleApp.class)
    public interface ComponentApp
    {
        // Root component for all other subcomponents
        ComponentUser plus(ModuleUser userModule);
    }

    @Component
    public interface ComponentUser
    {

    }
}
