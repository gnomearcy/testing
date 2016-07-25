package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import javax.inject.Singleton;

import dagger.Component;
import eu.span.dev.osijek.di.compdepend.module.ModuleDummy;

@Singleton
@Component(modules = {ModuleDummy.class})
public interface TESTCompModule
{
//    Motor dajMotor();
//    Vehicle dajSredstvo();
//    void injectajUActivity(TestDi activity);
//
//    Activity dajMiActivity();
}
