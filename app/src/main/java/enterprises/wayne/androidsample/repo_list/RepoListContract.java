package enterprises.wayne.androidsample.repo_list;

import java.util.List;

import enterprises.wayne.androidsample.entity.Repo;

/**
 * Created by ahmed on 9/17/2016.
 */
public class RepoListContract
{
    public interface View
    {
        void showProgress();
        void hideProgress();
        void showRepos(List<Repo> repoList);
        void showError(String error);
    }

    public interface Presenter
    {
        void getRepos(String userName);
        void subscribe(View view);
        void unsubscribe();
    }
}
