package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import android.app.Activity;
import javax.inject.Singleton;
import dagger.Component;
import eu.span.dev.osijek.di.compdepend.module.ModuleActivity;

@Singleton
@Component(modules = ModuleActivity.class)
public interface ComponentActivity
{
    Activity getActivity();
}
