package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import android.app.Activity;
import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import eu.span.dev.osijek.di.compdepend.module.ModuleApplication;
import eu.span.dev.osijek.di.compdepend.module.ModuleGlobalServices;

@Singleton
@Component
(
    modules = {ModuleGlobalServices.class, ModuleApplication.class}
)
public interface ComponentApplication
{
    // The type to which to inject has to be strong. Super class types do not apply
    void injectApp(Application application);

    void staviUActivity(Activity activity);
}
