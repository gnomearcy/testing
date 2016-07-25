package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1;

import android.app.Application;
import android.content.Context;

import eu.span.dev.osijek.di.scoping.BuildConfig;
import eu.span.dev.osijek.di.scoping.data.api.ModuleUser;
import eu.span.dev.osijek.di.scoping.data.model.User;
import timber.log.Timber;

public class ApplicationGitHubClient extends Application
{
    private ComponentApp appComponent;
    private ComponentUser userComponent;

    public static ApplicationGitHubClient get(Context context)
    {
        return (ApplicationGitHubClient) context.getApplicationContext();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        if(BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }

        initAppComponent();
    }

    private void initAppComponent()
    {
        // TODO inject the application here after build
//        appComponent = DaggerComponentApp.builder()
    }

    public ComponentUser createComponentUser(User user)
    {
        userComponent = appComponent.plus(new ModuleUser(user));
        return userComponent;
    }

    public void releaseUserComponent()
    {
        userComponent = null;
    }

    public ComponentApp getComponentApp()
    {
        return appComponent;
    }

    public ComponentUser getComponentUser()
    {
        return userComponent;
    }
}
