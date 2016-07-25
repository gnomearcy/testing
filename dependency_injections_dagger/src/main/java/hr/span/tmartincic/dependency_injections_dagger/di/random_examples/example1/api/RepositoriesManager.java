package eu.span.dev.osijek.di.scoping.data.api;

import com.google.common.collect.ImmutableList;

import java.util.List;

import eu.span.dev.osijek.di.scoping.data.model.Repository;
import eu.span.dev.osijek.di.scoping.data.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import eu.span.dev.osijek.di.scoping.data.api.response.RepositoryResponse;

// Repository presenter
public class RepositoriesManager
{
    private User user;
    private GithubApiService service;

    public RepositoriesManager(User user, GithubApiService service)
    {
        this.user = user;
        this.service = service;
    }

    public Observable<ImmutableList<Repository>> getUsersRepositories()
    {
        return service.getUsersRepositories(user.login)
                .map(new Func1<List<RepositoryResponse>, ImmutableList<Repository>>() {
                    @Override
                    public ImmutableList<Repository> call(List<RepositoryResponse> repositoriesListResponse) {
                        final ImmutableList.Builder<Repository> listBuilder = ImmutableList.builder();
                        for (RepositoryResponse repositoryResponse : repositoriesListResponse) {
                            Repository repository = new Repository();
                            repository.id = repositoryResponse.id;
                            repository.name = repositoryResponse.name;
                            repository.url = repositoryResponse.url;
                            listBuilder.add(repository);
                        }
                        return listBuilder.build();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
