package eu.span.dev.osijek.di.scoping.data.ui.component;

import dagger.Subcomponent;
import trainer.blanka.hr.testdagger.scoping_1.ActivityScope;
import eu.span.dev.osijek.di.scoping.data.ui.module.ModuleActivitySplash;
import eu.span.dev.osijek.di.scoping.data.ui.view.ActivitySplash;

// This component should be only alive during the lifecycle of an ActivitySplash
@ActivityScope
@Subcomponent(modules = ModuleActivitySplash.class)
public interface ComponentActivitySplash
{
    ActivitySplash injecet(ActivitySplash activity);
}
