package eu.span.dev.osijek.di.compdepend.module;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.ColorRandomizer;
import eu.span.dev.osijek.di.compdepend.model.Dye;

@Module
public class ModuleDye
{
    @Provides
    Dye provideDye(ColorRandomizer colorRandomizer)
    {
        return new Dye(colorRandomizer);
    }
}
