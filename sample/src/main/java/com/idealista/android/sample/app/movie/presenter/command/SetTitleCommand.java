package com.idealista.android.sample.app.movie.presenter.command;


import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movie.view.MovieView;
import com.idealista.android.elves.usecase.Command;

public class SetTitleCommand extends Command<MovieView> {

    private final MovieModel movieModel;

    public SetTitleCommand(MovieView view, MovieModel movieModel) {
        super(view);
        this.movieModel = movieModel;
    }

    public void execute() {
        view.setTitle(movieModel.getTitle());
    }

    @Override
    public void cancel() {

    }
}
