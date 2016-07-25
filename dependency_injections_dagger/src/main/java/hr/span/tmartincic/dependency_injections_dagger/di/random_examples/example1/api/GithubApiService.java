package eu.span.dev.osijek.di.scoping.data.api;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import eu.span.dev.osijek.di.scoping.data.api.response.RepositoryResponse;
import eu.span.dev.osijek.di.scoping.data.api.response.UserResponse;

public interface GithubApiService
{
    @GET("/users/{username}")
    Observable<UserResponse> getUser(
            @Path("username") String username
    );

    @GET("/users/{username}/repos")
    Observable<List<RepositoryResponse>> getUsersRepositories(
        @Path("username") String username
    );
}
