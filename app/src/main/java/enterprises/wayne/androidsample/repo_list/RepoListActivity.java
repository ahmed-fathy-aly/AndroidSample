package enterprises.wayne.androidsample.repo_list;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;

import enterprises.wayne.androidsample.R;
import enterprises.wayne.androidsample.data.GitHubAPIService;
import enterprises.wayne.androidsample.entity.Repo;
import enterprises.wayne.androidsample.ui.BaseFragmentActivity;
import timber.log.Timber;

public class RepoListActivity extends BaseFragmentActivity
{

    @Override
    protected Fragment getFragment()
    {
        return RepoListFragment.newInstance();
    }


}
