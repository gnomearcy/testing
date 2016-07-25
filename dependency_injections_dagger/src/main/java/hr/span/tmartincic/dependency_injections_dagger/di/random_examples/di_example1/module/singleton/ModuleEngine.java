package eu.span.dev.osijek.di.compdepend.module.singleton;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.Engine;
import eu.span.dev.osijek.di.compdepend.model.Fuel;

//@Singleton
@Module
public class ModuleEngine
{
    // TODO does this have to be a singleton?
    // TODO do we need a field of type Engine here?
    @Singleton
    @Provides
    Engine provideEngine(Fuel fuel)
    {
        return new Engine(fuel);
    }
}
