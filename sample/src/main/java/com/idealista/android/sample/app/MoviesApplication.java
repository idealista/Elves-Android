package com.idealista.android.sample.app;

import com.idealista.android.elves.App;
import com.idealista.android.elves.tasks.AndroidExecutors;
import com.idealista.android.elves.view.mvp.presenter.PresenterFactory;
import com.idealista.android.sample.app.common.presenter.AppPresenterFactory;

public class MoviesApplication extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidExecutors.UIThreadExecutor.init(executor);
    }

    @Override
    public PresenterFactory createPresenterFactory() {
        return new AppPresenterFactory<>();
    }

}
