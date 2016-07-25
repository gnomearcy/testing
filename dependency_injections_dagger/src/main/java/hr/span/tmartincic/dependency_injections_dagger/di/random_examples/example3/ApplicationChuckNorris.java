package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example3;

import android.app.Application;

import eu.span.dev.osijek.dimvp.demo.components.DaggerComponentServices;
import eu.span.dev.osijek.dimvp.demo.components.ComponentServices;
import eu.span.dev.osijek.dimvp.demo.module.ModuleApplication;
import eu.span.dev.osijek.dimvp.demo.module.ModuleServices;

public class ApplicationChuckNorris extends Application
{
    private ComponentServices global;

    @Override
    public void onCreate()
    {
        super.onCreate();

        global = DaggerComponentServices.builder()
                .moduleApplication(new ModuleApplication(this))
                .moduleServices(new ModuleServices())
                .build();
    }

    public ComponentServices getGlobal()
    {
        return global;
    }
}
