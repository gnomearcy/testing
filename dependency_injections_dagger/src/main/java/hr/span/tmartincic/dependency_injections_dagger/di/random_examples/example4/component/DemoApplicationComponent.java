package eu.span.dev.osijek.testdi.component;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example4.DemoApplication;
import eu.span.dev.osijek.testdi.module.ApplicationModule;

@Component(modules = {ApplicationModule.class})
public interface DemoApplicationComponent
{
    DemoApplication injectApplication(DemoApplication application);
}
