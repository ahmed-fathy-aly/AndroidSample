package enterprises.wayne.androidsample.app;

import dagger.Component;
import enterprises.wayne.androidsample.repo_list.RepoListFragment;
import enterprises.wayne.androidsample.repo_list.RepoListPresenter;

/**
 * Created by ahmed on 9/22/2016.
 */
@Component(modules={AppModule.class})
public interface AppComponent
{
    void inject(RepoListPresenter repoListPresenter);
    void inject(RepoListFragment repoListFragment);

}

