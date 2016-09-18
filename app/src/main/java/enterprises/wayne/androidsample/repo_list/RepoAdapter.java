package enterprises.wayne.androidsample.repo_list;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import enterprises.wayne.androidsample.R;
import enterprises.wayne.androidsample.entity.Repo;

/**
 * Created by ahmed on 9/18/2016.
 */
public class RepoAdapter extends BaseQuickAdapter<Repo>
{
    public RepoAdapter()
    {
        super(android.R.layout.simple_list_item_1, new ArrayList<Repo>());
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Repo repo)
    {
        baseViewHolder.setText(android.R.id.text1, repo.getName());
    }
}
