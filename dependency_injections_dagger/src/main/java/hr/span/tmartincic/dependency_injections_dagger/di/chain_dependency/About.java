package hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency;

public class About
{
    /**
     *  We have a Comp1 which is a Singleton.
     *  We have a Comp2 which depends on Comp1.
     *      If we don't add a custom scope to Comp2, it doesn't compile.
     *  We have a Comp3 which depends on Comp3.
     */
}
