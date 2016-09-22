package enterprises.wayne.androidsample.repo_list;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import enterprises.wayne.androidsample.data.GitHubAPIService;
import enterprises.wayne.androidsample.entity.Repo;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.*;


/**
 * Created by ahmed on 9/18/2016.
 */
@RunWith(JUnit4.class)
public class RepoListPresenterTest
{
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock    public RepoListContract.View view;
    @Mock    public GitHubAPIService gitHubAPIService;
    @InjectMocks public RepoListPresenter presenter;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook()
        {
            @Override
            public Scheduler getMainThreadScheduler()
            {
                return Schedulers.immediate();
            }
        });

        presenter.subscribe(view);
    }

    @After
    public void tearDown()
    {
        RxAndroidPlugins.getInstance().reset();
        presenter.unsubscribe();
    }

    @Test
    public void testGetRepos() throws Exception
    {
        String correctUserName = "ahmed-fathy-aly";
        List<Repo> repoList = Arrays.asList(new Repo(1, "a"), new Repo(2, "b"));
        when(gitHubAPIService.getRepos(correctUserName)).thenReturn(repoList);

        presenter.getRepos(correctUserName);

        // verify the view methods called
        verify(view, timeout(100)).showProgress();
        verify(view, timeout(100)).showRepos(repoList);
        verify(view, timeout(100)).hideProgress();
    }


    @Test
    public void testGetReposError() throws Exception
    {
        String incorrectUserName = "ossama";
        String errorMessage = "I failed";
        when(gitHubAPIService.getRepos(incorrectUserName)).thenThrow(new IOException(errorMessage));

        presenter.getRepos(incorrectUserName);

        // verify the view methods called
        verify(view).showProgress();
        Thread.sleep(10); // added that for mockito not to fail
        verify(view).showError(errorMessage);
        verify(view).hideProgress();
    }

}