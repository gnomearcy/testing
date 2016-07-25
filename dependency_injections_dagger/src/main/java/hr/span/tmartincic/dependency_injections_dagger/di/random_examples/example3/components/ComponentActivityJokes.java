package eu.span.dev.osijek.dimvp.demo.components;


import dagger.Component;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example3.ActivityJokes;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example3.ActivityScope;

@ActivityScope
@Component(dependencies = {ComponentServices.class})
public interface ComponentActivityJokes
{
    void inject(ActivityJokes activity);
}
