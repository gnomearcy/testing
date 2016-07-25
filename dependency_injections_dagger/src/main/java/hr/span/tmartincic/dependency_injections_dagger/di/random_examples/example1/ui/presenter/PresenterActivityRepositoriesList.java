package eu.span.dev.osijek.di.scoping.data.ui.presenter;

import eu.span.dev.osijek.di.scoping.data.api.RepositoriesManager;
import eu.span.dev.osijek.di.scoping.data.ui.view.ActivityRepositoriesList;

public class PresenterActivityRepositoriesList
{
    // TODO change to IViewRepositoriesList
    private ActivityRepositoriesList view;
    private RepositoriesManager manager;

    public PresenterActivityRepositoriesList(ActivityRepositoriesList view, RepositoriesManager manager)
    {
        this.view = view;
        this.manager = manager;
    }

    public void loadRepositories()
    {
        view.showLoading(true);
    }
}
