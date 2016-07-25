package eu.span.dev.osijek.di.scoping.data.ui.view;

import trainer.blanka.hr.testdagger.scoping_1.data.model.User;

public interface IViewSplash
{
    void showLoading(boolean state);
    void showValidationError();
    void showRepositoriesListForUser(User user);
}
