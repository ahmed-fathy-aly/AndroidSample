package enterprises.wayne.androidsample.repo_list;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import enterprises.wayne.androidsample.data.GitHubAPIService;
import enterprises.wayne.androidsample.entity.Repo;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ahmed on 10/5/2016.
 */

public class GetReposUseCaseImp implements GetReposUseCase
{

    private GitHubAPIService mGitHubAPIService;

    @Inject
    public GetReposUseCaseImp(GitHubAPIService gitHubAPIService)
    {
        mGitHubAPIService = gitHubAPIService;
    }

    /**
     * gets the repos from the data source on another thread
     */
    public void execute(String userName, Callback callback)
    {
        Observable
                .defer(() -> {
                    try
                    {
                        return rx.Observable.just(mGitHubAPIService.getRepos(userName));
                    } catch (IOException e)
                    {
                        return rx.Observable.error(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>()
                {
                    @Override
                    public void onCompleted()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        callback.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repo> repoList)
                    {
                        callback.success(repoList);
                    }
                });

    }

}
