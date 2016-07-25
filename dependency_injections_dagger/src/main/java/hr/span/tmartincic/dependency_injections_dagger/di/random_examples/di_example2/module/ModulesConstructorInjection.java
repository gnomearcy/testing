package eu.span.dev.osijek.di.compdepend.module;


import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example2.ActivityDiComponentDependency2;

public class ModulesConstructorInjection
{

    @Component
    public interface ModuleThermosiphon
    {
       void inject(ActivityDiComponentDependency2 act);
    }

    @Singleton
    public static class Thermosiphon
    {
        Heater heater;

        @Inject
        public Thermosiphon(Heater heater)
        {
            this.heater = heater;
        }
    }

    public static class Heater
    {
//        @Inject
        public Heater(){}
    }
}
