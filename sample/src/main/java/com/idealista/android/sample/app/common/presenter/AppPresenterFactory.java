package com.idealista.android.sample.app.common.presenter;


import com.idealista.android.elvesandroid.navigator.view.PresenterFactory;
import com.idealista.android.mvp.NullPresenter;
import com.idealista.android.mvp.Presenter;
import com.idealista.android.mvp.View;
import com.idealista.android.sample.app.movie.presenter.MoviePresenter;
import com.idealista.android.sample.app.movie.view.MovieView;
import com.idealista.android.sample.app.movies.presenter.MoviesPresenter;
import com.idealista.android.sample.app.movies.view.MoviesView;

public class AppPresenterFactory<P extends Presenter> implements PresenterFactory {

    public AppPresenterFactory() {
    }

    @Override
    public P getPresenter(View view) {
        Presenter presenter;

        if (view instanceof MoviesView) {
            MoviesView castedView = (MoviesView) view;
            presenter = new MoviesPresenter(castedView);
        } else if (view instanceof MovieView) {
            MovieView castedView = (MovieView) view;
            presenter = new MoviePresenter(castedView);
        } else {
            presenter = new NullPresenter(view);
        }

        return (P) presenter;
    }

}
