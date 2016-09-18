package enterprises.wayne.androidsample.data;

import java.io.IOException;
import java.util.List;

import enterprises.wayne.androidsample.entity.Repo;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import timber.log.Timber;

/**
 * Created by ahmed on 9/17/2016.
 * manages making REST requests with the Github API
 */
public class GitHubAPIService
{
    /*constants */
    public static final String BASE_URL = "https://api.github.com";

    /*fields */
    private API api;

    public GitHubAPIService()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    /**
     * returns a list of repos made by that user
     * synchronous
     */
    public List<Repo> getRepos(String userName) throws IOException
    {
        return api.getRepos(userName).execute().body();
    }

    private interface API
    {
        @GET("/users/{user_name}/repos")
        Call<List<Repo>> getRepos(@Path("user_name") String userName);
    }

}
