package enterprises.wayne.androidsample.repo_list;

import java.util.List;

import enterprises.wayne.androidsample.entity.Repo;

/**
 * Created by ahmed on 10/5/2016.
 */

public interface GetReposUseCase
{
    void execute(String useName, Callback callback);

    interface Callback
    {
        void success(List<Repo> repoList);
        void fail(String message);
    }

}
