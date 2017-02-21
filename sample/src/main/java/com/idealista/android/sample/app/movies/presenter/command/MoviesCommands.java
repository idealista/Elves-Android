package com.idealista.android.sample.app.movies.presenter.command;


import com.idealista.android.sample.app.movies.presenter.command.usecase.MoviesUseCases;
import com.idealista.android.sample.app.movies.view.MoviesView;

public class MoviesCommands {

    private MoviesView view;
    private MoviesUseCases useCases;

    public MoviesCommands(MoviesView view, MoviesUseCases useCases) {
        this.view = view;
        this.useCases = useCases;
    }

    public GetTitleCommand titleCommand(){
        return new GetTitleCommand(view, useCases);
    }

}
