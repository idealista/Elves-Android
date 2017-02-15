package com.idealista.android.sample.app;

import com.idealista.android.elves.navigator.App;
import com.idealista.android.elves.navigator.view.mvp.presenter.PresenterFactory;
import com.idealista.android.sample.app.common.presenter.AppPresenterFactory;

public class MoviesApplication extends App {

    @Override
    public PresenterFactory createPresenterFactory() {
        return new AppPresenterFactory<>();
    }

}
