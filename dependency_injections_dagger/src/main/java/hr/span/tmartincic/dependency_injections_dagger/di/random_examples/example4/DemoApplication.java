package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example4;

import android.app.Application;

import eu.span.dev.osijek.testdi.component.DaggerDemoApplicationComponent;
import eu.span.dev.osijek.testdi.component.DemoApplicationComponent;
import eu.span.dev.osijek.testdi.module.ApplicationModule;

// Lives as long as the process lives
public class DemoApplication extends Application
{
    private DemoApplicationComponent component;

    @Override
    public void onCreate()
    {
        super.onCreate();

        component = DaggerDemoApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        component.injectApplication(this);
    }

    public DemoApplicationComponent getComponent()
    {
        return component;
    }
}
