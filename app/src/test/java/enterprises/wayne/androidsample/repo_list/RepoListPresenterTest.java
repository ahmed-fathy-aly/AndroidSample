package enterprises.wayne.androidsample.repo_list;

import android.app.Instrumentation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import enterprises.wayne.androidsample.app.AppModule;
import enterprises.wayne.androidsample.data.GitHubAPIService;
import enterprises.wayne.androidsample.entity.Repo;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;


/**
 * Created by ahmed on 9/18/2016.
 */
@RunWith(JUnit4.class)
public class RepoListPresenterTest
{
    @Test
    public void testGetRepos() throws Exception
    {
        List<Repo> MOCK_REPO = Arrays.asList(new Repo(1, "a"), new Repo(2, "b"));
        RepoListPresenter presenter = new RepoListPresenter(new GetReposUseCase()
        {
            @Override
            public void execute(String useName, Callback callback)
            {
                callback.success(MOCK_REPO);
            }
        });
        RepoListContract.View view = Mockito.mock(RepoListContract.View.class);
        presenter.subscribe(view);
        presenter.getRepos("ahmed");

        // verify the view methods called
        verify(view, timeout(100)).showProgress();
        verify(view, timeout(100)).showRepos(MOCK_REPO);
        verify(view, timeout(100)).hideProgress();
    }

    @Test
    public void testGetReposError() throws Exception
    {
        String ERROR_MESSAGE = "You trusted me and I failed you";
        RepoListPresenter presenter = new RepoListPresenter(new GetReposUseCase()
        {
            @Override
            public void execute(String useName, Callback callback)
            {
                callback.fail(ERROR_MESSAGE);
            }
        });
        RepoListContract.View view = Mockito.mock(RepoListContract.View.class);
        presenter.subscribe(view);
        presenter.getRepos("ahmed");

        // verify the view methods called
        verify(view).showProgress();
        Thread.sleep(10); // added that for mockito not to fail
        verify(view).showError(ERROR_MESSAGE);
        verify(view).hideProgress();
    }

}