package enterprises.wayne.androidsample.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import enterprises.wayne.androidsample.R;

/**
 * Created by ahmed on 9/18/2016.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        if (getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment())
                    .commit();
    }

    protected abstract Fragment getFragment();

}
