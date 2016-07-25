package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import javax.inject.Singleton;

import dagger.Component;
import eu.span.dev.osijek.di.compdepend.module.ModuleActivity;
import eu.span.dev.osijek.di.compdepend.module.ModulePrefs;

@Singleton
@Component(modules = {ModulePrefs.class, ModuleActivity.class})
public interface ComponentPrefs
{
//    SharedPreferences dajPrefs();
//    void injectIntoTestDiActivity(TestDi activity);
//    Vehicle dajSredstvo();
}
