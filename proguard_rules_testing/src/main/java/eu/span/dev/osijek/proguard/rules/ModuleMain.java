package eu.span.dev.osijek.proguard.rules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleMain
{
    @Provides
    @Singleton
    public Summator provideHelperWithAddition()
    {
        return new Summator();
    }

    @Provides
    @Singleton
    public Calculator provideCalc(Summator s)
    {
        return new WrongCalculator(s);
    }
}
