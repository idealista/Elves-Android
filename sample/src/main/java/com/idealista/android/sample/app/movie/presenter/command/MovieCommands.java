package com.idealista.android.sample.app.movie.presenter.command;


import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movie.view.MovieView;

public class MovieCommands {

    private MovieView view;

    public MovieCommands(MovieView view) {
        this.view = view;
    }

    public SetTitleCommand setTitle(MovieModel movie) {
        return new SetTitleCommand(view, movie);
    }

}
