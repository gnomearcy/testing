package eu.span.dev.osijek.testdi.component;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example4.DemoActivity;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example4.DemoFragment;
import eu.span.dev.osijek.testdi.module.ActivityModule;

@Component(
        modules = ActivityModule.class,
        dependencies = DemoApplicationComponent.class
)
public interface DemoActivityComponent extends DemoFragment.Injector
{
        DemoActivity injectActivity(DemoActivity activity);
}
