package com.idealista.android.sample.app.movies.presenter.command;


import com.idealista.android.mvp.AutoStoppable;
import com.idealista.android.sample.app.movies.usecase.GetTitlesUseCase;
import com.idealista.android.sample.app.movies.view.MainView;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.usecase.Command;
import com.idealista.android.usecase.UiCommand;

import java.util.List;

public class GetTitleCommand extends Command<MainView> implements UiCommand<List<String>, MoviesModel> {

    private final GetTitlesUseCase useCase;

    public GetTitleCommand(MainView view, AutoStoppable autoStoppable) {
        super(view, autoStoppable);
        useCase = new GetTitlesUseCase();
    }

    @Override
    public void execute() {
        super.execute();
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
        view.clearMovies();
        view.addMovies(movies);
    }

    @Override
    public void error(Exception error) {

    }
}
