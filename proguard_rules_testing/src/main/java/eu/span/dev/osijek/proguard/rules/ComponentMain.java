package eu.span.dev.osijek.proguard.rules;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ModuleMain.class)
public interface ComponentMain
{
    Calculator provideCalculator();
    void inject(FragmentMain fragmentMain);
}
