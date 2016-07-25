package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1.api;

// Presenter for the User view?
public class UserManager
{
    private GithubApiService githubApiService;

    public UserManager(GithubApiService service)
    {
        this.githubApiService = service;
    }

    public Observable<User> getUser(String username) {
        return githubApiService.getUser(username)
                .map(returnFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Func1<UserResponse, User> returnFunc()
    {
        return new Func1<UserResponse, User>()
        {
            @Override
            public User call(UserResponse userResponse)
            {
                User user = new User();
                user.login = userResponse.login;
                user.id = userResponse.id;
                user.url = userResponse.url;
                user.email = userResponse.email;
                return user;
            }
        };
    }
}
