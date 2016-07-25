package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example2;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule
{
    private final Activity activity;

    ActivityModule(Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    Activity activity()
    {
        return this.activity;
    }
}
