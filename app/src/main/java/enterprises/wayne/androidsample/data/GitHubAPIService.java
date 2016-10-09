package enterprises.wayne.androidsample.data;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import enterprises.wayne.androidsample.entity.Repo;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import timber.log.Timber;

import static android.R.attr.country;

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
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    private static class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Timber.d("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Timber.d("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            return response;
        }
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
