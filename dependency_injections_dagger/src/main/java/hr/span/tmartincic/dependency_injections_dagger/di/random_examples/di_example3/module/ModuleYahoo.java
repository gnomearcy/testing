package eu.span.dev.osijek.di.compdepend.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3.ClientProvider;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3.NetClient;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3.YahooNetClientImpl;

@Module
public class ModuleYahoo implements ClientProvider
{
    private String key;

    public ModuleYahoo(String yahooKey)
    {
        this.key = yahooKey;
    }

    @Provides
    @Named("yahoo")
    @Override
    public NetClient provideClient()
    {
        return new YahooNetClientImpl(key);
    }
}
