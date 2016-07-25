package eu.span.dev.osijek.di.scoping.data.api;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.scoping.BuildConfig;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module
public class ModuleGithubApi
{
    // Only one instance of this client
    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient()
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        return okHttpClient;
    }

    // Only one instance of the adapter
    @Singleton
    @Provides
    public RestAdapter provideRestAdapter(Application application, OkHttpClient client)
    {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(client))
                .setEndpoint(application.getString(R.string.endpoint));

        if(BuildConfig.DEBUG)
        {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    public UserManager provideUserManager(GithubApiService githubService)
    {
        return new UserManager(githubService);
    }

    // Create the API interface from this adapter
    @Provides
    @Singleton
    public GithubApiService provideGitHubService(RestAdapter adapter)
    {
        return adapter.create(GithubApiService.class);
    }
}
