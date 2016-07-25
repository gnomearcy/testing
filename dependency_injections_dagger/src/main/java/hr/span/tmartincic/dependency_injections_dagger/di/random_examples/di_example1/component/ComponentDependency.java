package eu.span.dev.osijek.di.compdepend.component;

import javax.inject.Singleton;

import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example1.ActivityComponentDependency;
import eu.span.dev.osijek.di.compdepend.model.Engine;
import eu.span.dev.osijek.di.compdepend.model.Fuel;
import eu.span.dev.osijek.di.compdepend.module.singleton.ModuleEngine;
import eu.span.dev.osijek.di.compdepend.module.singleton.ModuleFuel;

public class ComponentDependency
{
//    @Singleton
//    @Component(modules = {ModuleSeat.class, ModuleFabric.class, ModuleDye.class, ModuleColorRandomizer.class, ModuleCar.class},
//            dependencies = ComponentEngine.class
//    )
//    public interface ComponentAccessories
//    {
//        Car getACar();
//        void injectIntoAct(TestDi act);
//    }


    @Singleton
    @Component(modules = {ModuleEngine.class, ModuleFuel.class})
    public interface ComponentEngine
    {
        //    void injectIntoAct(TestDi act);

        // Return types - explicitly exposed
        Engine getEngine();
        Fuel getFuels();

        void inject(ActivityComponentDependency act);
    }

}
