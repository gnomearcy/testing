package hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.Consts;

@Module
public class Module2
{
    /**
     *  If Comp2 does not explicitly provide Module2Object via:
     *      Module2Object getIt();
     *  we can:
     *      - annotate with @Singleton - different value is computed each time we call inject from Comp2
     *      - annotate with @SingletonComp2 - different value each time
     *      - leave without annotation - different value each time
     *      #basically non-singleton behaviour
     *  Otherwise:
     *      - @Singleton - throws error that Comp2 cannot reference bindings with different scope
     *      - @SingletonComp2 - this is fine - singleton behavior
     *      - leave without annotation - this is fine - non-singleton behavior
     */
    @Comp2Scope
    @Provides
    public Module2Object provideModule2Object(Module1.Module1Object dependency)
    {
        return new Module2Object(dependency);
    }

    public static class Module2Object
    {
        public int value;
        public Module1.Module1Object dependency;
        public String constructor;

        // Required annotation since @Provides method for non-dependent Module2Object returns
        // a new instance of Module2Object through non-arg constructor. By annotating with @Inject
        // Dagger will know to manually create Module2Object via "new" keyword.
        @Inject
        public Module2Object()
        {
            value = new Random().nextInt(123123);
            dependency = null;
            constructor = "no params";
        }

        public Module2Object(Module1.Module1Object dependency)
        {
            value = new Random().nextInt(123233);
            this.dependency = dependency;
            constructor = "params";
        }
    }
}
