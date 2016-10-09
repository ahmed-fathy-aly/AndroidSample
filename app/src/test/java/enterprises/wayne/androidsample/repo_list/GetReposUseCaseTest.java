package enterprises.wayne.androidsample.repo_list;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import enterprises.wayne.androidsample.data.GitHubAPIService;
import enterprises.wayne.androidsample.entity.Repo;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by ahmed on 10/5/2016.
 */
public class GetReposUseCaseTest
{
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    GitHubAPIService mGitHubAPIService;

    @Mock
    GetReposUseCaseImp.Callback mCallback;

    @InjectMocks
    GetReposUseCaseImp mGetRepoUseCase;

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

    }

    @After
    public void tearDown()
    {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void testGetRepos() throws Exception
    {
        List<Repo> repoList = Arrays.asList(new Repo(1, "a"), new Repo(2, "b"));
        Mockito.when(mGitHubAPIService.getRepos("ahmed")).thenReturn(repoList);

        mGetRepoUseCase.execute("ahmed", mCallback);
        Mockito.verify(mCallback, Mockito.timeout(1000)).success(repoList);

    }
}