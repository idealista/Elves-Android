package com.idealista.android.sample.app;

import com.idealista.android.elvesandroid.navigator.App;
import com.idealista.android.view.mvp.PresenterFactory;
import com.idealista.android.sample.app.common.presenter.AppPresenterFactory;

public class MoviesApplication extends App {

    @Override
    public PresenterFactory createPresenterFactory() {
        return new AppPresenterFactory<>();
    }

}
