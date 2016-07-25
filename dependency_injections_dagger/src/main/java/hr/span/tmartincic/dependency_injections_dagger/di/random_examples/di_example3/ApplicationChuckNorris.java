package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import android.app.Application;

import eu.span.dev.osijek.di.compdepend.module.ModuleApplication;
import eu.span.dev.osijek.di.compdepend.module.ModuleGlobalServices;

public class ApplicationChuckNorris extends Application
{
    private ComponentApplication global;

    @Override
    public void onCreate()
    {
        super.onCreate();

        global = DaggerComponentApplication.builder()
                .moduleApplication(new ModuleApplication(this))
                .moduleGlobalServices(new ModuleGlobalServices())
                .build();

        global.injectApp(this);
    }

    public ComponentApplication getGlobal()
    {
        return global;
    }
}
