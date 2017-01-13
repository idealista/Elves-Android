package com.idealista.android.sample.app.movies;


import com.idealista.android.mvp.View;
import com.idealista.android.mvp.EmptyPresenter;
import com.idealista.android.mvp.Presenter;
import com.idealista.android.sample.app.movies.presenter.MainPresenter;
import com.idealista.android.sample.app.movies.view.MainView;

public class PresenterFactory<P extends Presenter> {

    public PresenterFactory() {
    }

    public P getPresenter(View view) {
        Presenter presenter;

        if (view instanceof MainView) {
            MainView castedView = (MainView) view;
            presenter = new MainPresenter(castedView);
        } else {
            presenter = new EmptyPresenter(view);
        }

        return (P) presenter;
    }

}
