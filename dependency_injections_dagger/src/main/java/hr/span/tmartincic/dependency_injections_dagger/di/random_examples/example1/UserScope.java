package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope
{
}
