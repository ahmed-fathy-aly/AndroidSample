package enterprises.wayne.androidsample.repo_list;

import java.io.IOException;
import java.util.List;

import enterprises.wayne.androidsample.data.GitHubAPIService;
import enterprises.wayne.androidsample.entity.Repo;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ahmed on 9/17/2016.
 */
public class RepoListPresenter implements RepoListContract.Presenter
{
    private RepoListContract.View mView;
    private GitHubAPIService mGitHubAPIService;

    public RepoListPresenter()
    {
        setGithubService(mGitHubAPIService);
    }


    /**
     * made public for testing only
     * no need to call it
     */
    public void setGithubService(GitHubAPIService githubService)
    {
        mGitHubAPIService = githubService;
    }

    @Override
    public void getRepos(String userName)
    {
        if (mView != null)
            mView.showProgress();
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
                        if (mView != null)
                            mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        if (mView != null)
                        {
                            mView.showError(e.getMessage());
                            mView.hideProgress();
                        }
                    }

                    @Override
                    public void onNext(List<Repo> repoList)
                    {
                        if (mView != null)
                            mView.showRepos(repoList);
                    }
                });
    }

    @Override
    public void subscribe(RepoListContract.View view)
    {
        mView = view;
    }

    @Override
    public void unsubscribe()
    {
        mView = null;
    }
}
