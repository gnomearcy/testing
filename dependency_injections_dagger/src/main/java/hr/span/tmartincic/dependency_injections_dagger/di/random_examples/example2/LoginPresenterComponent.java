package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example2;

import javax.inject.Singleton;

import dagger.Component;
import eu.span.dev.osijek.dimvp.demo2.presenter.LoginPresenter;

@Singleton
@Component(modules = {LoginPresenterModule.class})
public interface LoginPresenterComponent
{
    LoginPresenter provideLoginPresenter();
}
