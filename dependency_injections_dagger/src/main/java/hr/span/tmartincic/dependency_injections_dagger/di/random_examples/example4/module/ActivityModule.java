package eu.span.dev.osijek.testdi.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule
{
    private final Activity activity;

    public ActivityModule(Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    public Activity activity()
    {
        return this.activity;
    }
}
