package hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonComp3{}
