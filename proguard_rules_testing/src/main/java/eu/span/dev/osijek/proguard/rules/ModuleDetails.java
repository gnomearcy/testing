package eu.span.dev.osijek.proguard.rules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleDetails
{

    @Provides
    @Singleton
    public Divider giveMeDivider()
    {
        return new Divider();
    }
}
