package eu.span.dev.osijek.dimvp.demo2.presenter;

import eu.span.dev.osijek.dimvp.demo2.interactor.SynchronousLoginInteractor;
import eu.span.dev.osijek.dimvp.demo2.view.ILoginView;

public class LoginPresenter implements ILoginPresenter
{
    // View we are presenting the data
    private ILoginView view;
    private SynchronousLoginInteractor interactor;

    // Dependency injection - LoginPresenter depends on ILoginView and SycnrhnousLoginInteractor
    public LoginPresenter(ILoginView view, SynchronousLoginInteractor interactor)
    {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void attemptLogin(String username, String password)
    {
        boolean isValid = interactor.validateCredentials(username, password);

        if(isValid)
        {
            view.navigateToListActivity();
        }
        else
        {
            view.loginFailed();
        }
    }
}
