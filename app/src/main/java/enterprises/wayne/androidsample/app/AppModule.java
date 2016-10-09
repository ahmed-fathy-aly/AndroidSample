package enterprises.wayne.androidsample.app;

import dagger.Module;
import dagger.Provides;
import enterprises.wayne.androidsample.data.GitHubAPIService;
import enterprises.wayne.androidsample.repo_list.GetReposUseCase;
import enterprises.wayne.androidsample.repo_list.GetReposUseCaseImp;
import enterprises.wayne.androidsample.repo_list.RepoListPresenter;

/**
 * Created by ahmed on 9/22/2016.
 */
@Module
public class AppModule
{
    @Provides
    public GitHubAPIService provideGithubAPIService()
    {
        return new GitHubAPIService();
    }

    @Provides
    public GetReposUseCase provideGetRepoUseCase(GitHubAPIService gitHubAPIService)
    {
        return new GetReposUseCaseImp(gitHubAPIService);
    }
    @Provides
    public RepoListPresenter provideRepoListPresenter(GetReposUseCaseImp getReposUseCase)
    {
        return new RepoListPresenter(getReposUseCase);
    }
}
