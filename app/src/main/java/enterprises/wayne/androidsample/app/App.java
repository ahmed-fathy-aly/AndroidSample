package enterprises.wayne.androidsample.app;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by ahmed on 9/17/2016.
 */
public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
