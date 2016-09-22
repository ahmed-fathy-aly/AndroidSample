package enterprises.wayne.androidsample.repo_list;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import enterprises.wayne.androidsample.R;
import enterprises.wayne.androidsample.app.App;
import enterprises.wayne.androidsample.entity.Repo;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepoListFragment extends Fragment implements RepoListContract.View
{
    /* UI */
    @Bind(R.id.edit_text_user_name)
    EditText editTextUserName;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.recycler_view_repos)
    RecyclerView recyclerViewRepos;
    @Bind(R.id.view_container)
    View viewContainer;

    /* fields */
    private RepoAdapter adapterRepos;
    @Inject public RepoListPresenter mPresenter;

    public RepoListFragment()
    {
        // Required empty public constructor
    }

    public static RepoListFragment newInstance()
    {
        return new RepoListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        ButterKnife.bind(this,view);

        // setup adapter
        adapterRepos = new RepoAdapter();
        recyclerViewRepos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRepos.setAdapter(adapterRepos);

        // create presenter
        App app = (App) getActivity().getApplication();
        app.getComponent().inject(this);
        mPresenter.subscribe(this);

        // add listener for when username is changed and debounce it to decrease requests
        RxTextView.textChangeEvents(editTextUserName)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>()
                {
                    @Override
                    public void onCompleted()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent)
                    {
                        mPresenter.getRepos(textViewTextChangeEvent.text().toString());
                    }
                });
        return view;
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.unsubscribe();
    }

    @Override
    public void showProgress()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRepos(List<Repo> repoList)
    {
        adapterRepos.setNewData(repoList);
    }

    @Override
    public void showError(String error)
    {
        Snackbar.make(viewContainer, error, Snackbar.LENGTH_SHORT).show();
    }

}
