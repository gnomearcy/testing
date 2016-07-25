package eu.span.dev.osijek.di.scoping.data.ui.module;

import dagger.Module;
import dagger.Provides;
import trainer.blanka.hr.testdagger.scoping_1.ActivityScope;
import trainer.blanka.hr.testdagger.scoping_1.data.api.RepositoriesManager;
import eu.span.dev.osijek.di.scoping.data.ui.presenter.PresenterActivityRepositoriesList;
import eu.span.dev.osijek.di.scoping.data.ui.view.ActivityRepositoriesList;

@Module
public class ModuleActivityRepositoriesList
{
    // TODO change to IViewRepositoriesList
    private ActivityRepositoriesList activity;

    public ModuleActivityRepositoriesList(ActivityRepositoriesList repositoriesListActivity) {
        this.activity = repositoriesListActivity;
    }

    @Provides
    @ActivityScope
    ActivityRepositoriesList provideRepositoriesListActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    PresenterActivityRepositoriesList provideRepositoriesListActivityPresenter(RepositoriesManager repositoriesManager) {
        return new PresenterActivityRepositoriesList(activity, repositoriesManager);
    }
}
