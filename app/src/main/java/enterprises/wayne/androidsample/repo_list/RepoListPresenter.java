package enterprises.wayne.androidsample.repo_list;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import enterprises.wayne.androidsample.entity.Repo;

/**
 * Created by ahmed on 9/17/2016.
 */
public class RepoListPresenter implements RepoListContract.Presenter
{
    private RepoListContract.View mView;
    private GetReposUseCase mGetReposUseCase;

    @Inject
    public RepoListPresenter(GetReposUseCase getReposUseCase)
    {
        mGetReposUseCase = getReposUseCase;
    }

    @Override
    public void getRepos(String userName)
    {
        if (mView != null)
            mView.showProgress();

        mGetReposUseCase.execute(userName,
                new GetReposUseCaseImp.Callback()
                {
                    @Override
                    public void success(List<Repo> repoList)
                    {
                        if (mView != null)
                        {
                            mView.showRepos(repoList != null ? repoList : new ArrayList<Repo>());
                            mView.hideProgress();
                        }
                    }

                    @Override
                    public void fail(String message)
                    {
                        if (mView != null)
                        {
                            mView.showError(message);
                            mView.hideProgress();
                        }

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
