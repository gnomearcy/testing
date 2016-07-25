package eu.span.dev.osijek.di.scoping.data.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.common.collect.ImmutableList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnItemClick;
import eu.span.dev.osijek.di.scoping.R;
import eu.span.dev.osijek.di.scoping.data.model.Repository;
import eu.span.dev.osijek.di.scoping.data.ui.adapter.AdapterRepositoriesList;
import eu.span.dev.osijek.di.scoping.data.ui.presenter.PresenterActivityRepositoriesList;
import eu.span.dev.osijek.di.scoping.data.utils.AnalyticsManager;

public class ActivityRepositoriesList extends ActivityBase
{
    @Bind(R.id.lvRepositories)
    ListView lvRepositories;

    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;

    @Inject
    PresenterActivityRepositoriesList presenter;

    @Inject
    AnalyticsManager analyticsManager;

    private AdapterRepositoriesList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);
    }

    @Override
    protected void setupActivityComponent()
    {

    }

    // TODO move to IViewRepositoriesList
    public void showLoading(boolean loading)
    {
        lvRepositories.setVisibility(loading ? View.GONE : View.VISIBLE);
        pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    public void setRepositories(ImmutableList<Repository> objects)
    {
        adapter.clear();
        adapter.addAll(objects);
    }

    @OnItemClick(R.id.lvRepositories)
    public void onRepositoryClick(int position)
    {
        Repository repository = adapter.getItem(position);
    }
}