package eu.span.dev.osijek.di.scoping.data.utils;

import android.app.Application;

import timber.log.Timber;


public class AnalyticsManager
{
    private Application app;

    public AnalyticsManager(Application app)
    {
        this.app = app;
    }

    public void logScreenView(String screenName)
    {
        Timber.d("Logged screen name: " + screenName);
    }
}
