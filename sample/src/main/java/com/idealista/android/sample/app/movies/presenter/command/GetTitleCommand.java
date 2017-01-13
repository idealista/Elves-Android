package com.idealista.android.sample.app.movies.presenter.command;


import com.idealista.android.mvp.AutoStoppable;
import com.idealista.android.sample.app.movies.usecase.GetTitlesUseCase;
import com.idealista.android.sample.app.movies.view.MainView;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.usecase.Command;
import com.idealista.android.usecase.UiCommand;

import java.util.List;

public class GetTitleCommand implements Command, UiCommand<List<String>, MoviesModel> {

    private final MainView view;
    private final GetTitlesUseCase useCase;
    private final AutoStoppable autoStoppable;

    public GetTitleCommand(MainView view, AutoStoppable autoStoppable) {
        this.view = view;
        this.autoStoppable = autoStoppable;
        useCase = new GetTitlesUseCase();
    }

    @Override
    public void execute() {
        autoStoppable.addCommand(this);
        useCase.execute(this);
    }

    @Override
    public void cancel() {

    }

    @Override
    public MoviesModel background(List<String> strings) {
        MoviesModel movies = new MoviesModel();
        for (String string : strings) {
            movies.add(new MovieModel(string));
        }
        return movies;
    }

    @Override
    public void ui(MoviesModel movies) {
//        view.clearMovies();
        view.addMovies(movies);
    }

    @Override
    public void error(Exception error) {

    }
}
