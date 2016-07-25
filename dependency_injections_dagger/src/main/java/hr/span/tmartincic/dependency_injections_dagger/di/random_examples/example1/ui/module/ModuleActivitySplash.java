package eu.span.dev.osijek.di.scoping.data.ui.module;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1.ActivityScope;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1.HeavyLibraryWrapper;
import eu.span.dev.osijek.di.scoping.data.api.UserManager;
import eu.span.dev.osijek.di.scoping.data.ui.presenter.PresenterActivitySplash;
import eu.span.dev.osijek.di.scoping.data.ui.view.ActivitySplash;
import eu.span.dev.osijek.di.scoping.data.utils.Validator;

@Module
public class ModuleActivitySplash
{
    // TODO change this to IViewSplash and get the same effect
    private ActivitySplash activity;

    public ModuleActivitySplash(ActivitySplash activity)
    {
        this.activity = activity;
    }

    // Not a singleton -> memory leak
    @Provides
    @ActivityScope
    ActivitySplash provideActivitySplash()
    {
        return this.activity;
    }

    @Provides
    @ActivityScope
    PresenterActivitySplash
    providePresenterActivitySplash(Validator validator, UserManager userManager, HeavyLibraryWrapper wrapper)
    {
        return new PresenterActivitySplash(activity, validator, userManager, wrapper);
    }
}
