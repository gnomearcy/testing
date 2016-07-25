package eu.span.dev.osijek.di.compdepend.module;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.User;

@Module
public class ModuleUser
{
    private User user;

    public ModuleUser(String name, String lastname)
    {
        user = new User(name, lastname);
    }

    @Provides
    User provideUser()
    {
        return this.user;
    }
}
