package com.idealista.android.sample.app.movies.presenter.command;


import com.idealista.android.mvp.Presenter;
import com.idealista.android.sample.app.movies.presenter.command.usecase.GetTitlesUseCase;
import com.idealista.android.sample.app.movies.view.MoviesView;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.usecase.Command;
import com.idealista.android.usecase.UiCommand;

import java.util.List;

public class GetTitleCommand extends Command<MoviesView> implements UiCommand<List<String>, MoviesModel> {

    private final GetTitlesUseCase useCase;

    public GetTitleCommand(MoviesView view, Presenter presenter) {
        super(view);
        useCase = new GetTitlesUseCase();
        presenter.addCommand(this);
    }

    public void execute() {
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
