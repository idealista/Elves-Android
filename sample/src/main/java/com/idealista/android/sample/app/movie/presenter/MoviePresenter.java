package com.idealista.android.sample.app.movie.presenter;


import com.idealista.android.elvesandroid.navigator.view.mvp.Presenter;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movie.presenter.command.SetTitleCommand;
import com.idealista.android.sample.app.movie.view.MovieView;

public class MoviePresenter extends Presenter<MovieView> {

    private MovieModel movieModel;

    public MoviePresenter(MovieView view) {
        super(view);
    }

    @Override
    public void start() {
        new SetTitleCommand(view, movieModel).execute();
    }

    public void setMovie(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
