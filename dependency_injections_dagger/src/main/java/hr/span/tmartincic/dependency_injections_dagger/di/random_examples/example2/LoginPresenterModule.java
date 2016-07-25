package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.dimvp.demo2.interactor.SynchronousLoginInteractor;
import eu.span.dev.osijek.dimvp.demo2.presenter.LoginPresenter;
import eu.span.dev.osijek.dimvp.demo2.view.ILoginView;

@Module
public class LoginPresenterModule
{
    private final ILoginView loginView;

    public LoginPresenterModule(ILoginView loginView)
    {
        this.loginView = loginView;
    }

    @Provides @Singleton
    LoginPresenter provideLoginPresenter()
    {
        return new LoginPresenter(loginView, new SynchronousLoginInteractor());
    }
}
