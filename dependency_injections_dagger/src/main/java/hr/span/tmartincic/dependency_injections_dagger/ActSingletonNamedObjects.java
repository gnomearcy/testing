package hr.span.tmartincic.dependency_injections_dagger;

import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Named;

import hr.span.tmartincic.dependency_injections_dagger.di.singleton_named.Comp1;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_named.DaggerComp1;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_named.DaggerComp2;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_named.Module1;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_named.Module2;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_named.Comp2;

public class ActSingletonNamedObjects extends ActivityBase
{
//    @Inject
//    @Named(Constants.NO_SINGLETON_NO_DEPS)
//    public Module1.Module1Object noSingletonNoDeps;
//
//    @Inject
//    @Named(Constants.SINGLETON_NO_DEPS)
//    public Module1.Module1Object singletonNoDeps;

    @Inject
    @Named(ActSingletonNamedObjects.Constants.SINGLETON_DIFFERENT_DEPS)
    public Module2.Module2Object singletonDifferentDeps;

//
//    @Inject
//    @Named(ActSingletonNamedObjects.Constants.NO_SINGLETON_DIFFERENT_DEPS)
//    public Module2.Module2Object noSingletonDeps;

//    @Inject
//    @Named(ActSingletonNamedObjects.Constants.NO_SINGLETON_SINGLETON_DEPS)
//    public Module2.Module2Object noSingletonSingletonDeps;
//
//    @Inject
//    @Named(ActSingletonNamedObjects.Constants.SINGLETON_SINGLETON_DEPS)
//    public Module2.Module2Object singletonSingletonDeps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_singleton_named_objects);

        Comp1 comp1 = DaggerComp1.builder().module1(new Module1()).build();
        Module2 module2 = new Module2();
        Comp2 comp2 = DaggerComp2.builder().comp1(comp1).module2(module2).build();
        comp2.inject(this);

        breakpointPlaceholder();
    }

    public static final class Constants
    {
        public static final String NO_SINGLETON_NO_DEPS = "no_singleton_no_deps";
        public static final String SINGLETON_NO_DEPS = "singleton_no_deps";
        public static final String NO_SINGLETON_DIFFERENT_DEPS = "no_singleton_deps";
        public static final String SINGLETON_DIFFERENT_DEPS = "singleton_deps";
        public static final String NO_SINGLETON_SINGLETON_DEPS = "no_singleton_singleton_deps";
        public static final String SINGLETON_SINGLETON_DEPS = "singleton_singleton_deps";
    }
}