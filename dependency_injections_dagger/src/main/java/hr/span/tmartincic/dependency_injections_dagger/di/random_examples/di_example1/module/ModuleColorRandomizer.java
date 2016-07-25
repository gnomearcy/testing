package eu.span.dev.osijek.di.compdepend.module;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.ColorRandomizer;

@Module
public class ModuleColorRandomizer
{
    @Provides
    ColorRandomizer provideColorRandomizer()
    {
        return new ColorRandomizer();
    }
}
