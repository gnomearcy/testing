package eu.span.dev.osijek.di.compdepend.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleActivity
{
    private Activity activity;

    public ModuleActivity(Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity()
    {
        return this.activity;
    }
}
