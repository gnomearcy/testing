package eu.span.dev.osijek.di.scoping.data.api;

import dagger.Module;
import dagger.Provides;
import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1.UserScope;
import eu.span.dev.osijek.di.scoping.data.model.User;

@Module
public class ModuleUser
{
    private User user;

    public ModuleUser(User user)
    {
        this.user = user;
    }

    @Provides
    @UserScope
    User provideUser()
    {
        return this.user;
    }

    @Provides
    @UserScope
    RepositoriesManager provideRepositoriesManager(User user, GithubApiService service)
    {
        return new RepositoriesManager(user, service);
    }
}
