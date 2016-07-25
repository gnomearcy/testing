package eu.span.dev.osijek.di.compdepend.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3.ClientProvider;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3.GoogleNetClientImpl;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3.NetClient;

@Module
public class ModuleGoogle implements ClientProvider
{
    private String key;

    public ModuleGoogle(String googleKey)
    {
        this.key = googleKey;
    }

    @Provides
    @Named("google")
    @Override
    public NetClient provideClient()
    {
        return new GoogleNetClientImpl(key);
    }
}
