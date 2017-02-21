package com.idealista.android.sample.app.movie.presenter;


import com.idealista.android.sample.app.movie.presenter.command.MovieCommands;
import com.idealista.android.sample.app.movie.view.MovieView;

public class GetMoviePresenter {

    private final MovieView view;
    private MovieCommands commands;

    public GetMoviePresenter(MovieView view) {
        this.view = view;
        this.commands = new MovieCommands(view);
    }

    public MoviePresenter get(){
        return new MoviePresenter(view, commands);
    }

}
