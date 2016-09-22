package enterprises.wayne.androidsample.app;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by ahmed on 9/17/2016.
 */
public class App extends Application
{
    AppComponent mComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    public AppComponent getComponent()
    {
        if (mComponent == null)
            mComponent = DaggerAppComponent.builder().build();
        return mComponent;
    }

    public void setComponent(AppComponent component)
    {
        mComponent = component;
    }
}
