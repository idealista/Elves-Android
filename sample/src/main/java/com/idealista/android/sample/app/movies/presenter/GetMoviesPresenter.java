package com.idealista.android.sample.app.movies.presenter;


import com.idealista.android.sample.app.movies.presenter.command.MoviesCommands;
import com.idealista.android.sample.app.movies.presenter.command.usecase.MoviesUseCases;
import com.idealista.android.sample.app.movies.view.MoviesView;

public class GetMoviesPresenter {

    private MoviesView view;

    public GetMoviesPresenter(MoviesView view) {
        this.view = view;
    }

    public MoviesPresenter get() {
        return new MoviesPresenter(view, new MoviesCommands(view, new MoviesUseCases()));
    }

}
