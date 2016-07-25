package eu.span.dev.osijek.di.compdepend.module;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.Dye;
import eu.span.dev.osijek.di.compdepend.model.Fabric;
import eu.span.dev.osijek.di.compdepend.model.Seat;

@Module
public class ModuleSeat
{
    @Provides
    Seat provideSeat(Fabric fabric, Dye dye)
    {
        return new Seat(fabric, dye);
    }
}
