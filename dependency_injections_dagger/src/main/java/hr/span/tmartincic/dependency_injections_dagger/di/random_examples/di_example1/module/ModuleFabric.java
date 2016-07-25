package eu.span.dev.osijek.di.compdepend.module;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.Fabric;

@Module
public class ModuleFabric
{
    @Provides
    Fabric provideFabric()
    {
        return new Fabric();
    }
}
