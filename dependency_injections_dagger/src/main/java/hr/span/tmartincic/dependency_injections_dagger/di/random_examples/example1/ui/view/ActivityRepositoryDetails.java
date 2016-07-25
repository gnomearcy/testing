package eu.span.dev.osijek.di.scoping.data.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.span.dev.osijek.di.scoping.R;
import eu.span.dev.osijek.di.scoping.data.model.Repository;
import eu.span.dev.osijek.di.scoping.data.ui.presenter.PresenterActivityRepositoryDetails;
import eu.span.dev.osijek.di.scoping.data.utils.AnalyticsManager;

public class ActivityRepositoryDetails extends ActivityBase
{
    private static final String ARG_REPOSITORY = "arg_repository";

    @Bind(R.id.tvRepoName)
    TextView tvRepoName;

    @Bind(R.id.tvRepoDetails)
    TextView tvRepoDetails;

    @Bind(R.id.tvUserName)
    TextView tvUserName;

    @Inject
    AnalyticsManager analyticsManager;

    @Inject
    PresenterActivityRepositoryDetails presenter;

    private Repository repository;

    // Convenient way to start a new activity with data. Check Navigator pattern or Flow library
    public static void startWithRepository(Repository repository, Activity startingActivity)
    {
        Intent intent = new Intent(startingActivity, ActivityRepositoryDetails.class);
        intent.putExtra(ARG_REPOSITORY, repository);
        startingActivity.startActivity(intent);
    }

    // What to do on orientation change? save the repository in onSaveInstanceState?
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);
        ButterKnife.bind(this);
        analyticsManager.logScreenView(getClass().getName());

        repository = getIntent().getParcelableExtra(ARG_REPOSITORY);
        tvRepoName.setText(repository.name);
        tvRepoDetails.setText(repository.url);

        presenter.init();
    }

    @Override
    protected void setupActivityComponent()
    {

    }
}
