package com.idealista.android.sample.app.movie.presenter;


import com.idealista.android.elves.view.mvp.presenter.Presenter;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movie.presenter.command.MovieCommands;
import com.idealista.android.sample.app.movie.view.MovieView;

public class MoviePresenter extends Presenter<MovieView> {

    private final MovieCommands commands;
    private MovieModel movieModel;

    MoviePresenter(MovieView view, MovieCommands commands) {
        super(view);
        this.commands = commands;
    }

    @Override
    public void start() {
        commands.setTitle(movieModel).execute();
    }

    public void setMovie(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
