package eu.span.dev.osijek.di.compdepend.module.singleton;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.Fuel;
import eu.span.dev.osijek.di.compdepend.model.FuelBenzin;
import eu.span.dev.osijek.di.compdepend.model.FuelDiesel;

//@Singleton
@Module
public class ModuleFuel
{
    // TODO this will break? separate into two different modules?
    // TODO or specialize the return type?
    @Singleton
    @Provides
    Fuel provideBenzin()
    {
        return new FuelBenzin();
    }

    // TODO uncomment when you solve component dependency
//    Fuel provideDiesel()
//    {
//        return new FuelDiesel();
//    }
}
