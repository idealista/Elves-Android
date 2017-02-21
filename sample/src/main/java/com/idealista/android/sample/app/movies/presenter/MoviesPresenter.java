package com.idealista.android.sample.app.movies.presenter;

import com.idealista.android.elves.navigator.Navigator;
import com.idealista.android.elves.view.mvp.presenter.Presenter;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movies.presenter.command.GetTitleCommand;
import com.idealista.android.sample.app.movies.presenter.command.MoviesCommands;
import com.idealista.android.sample.app.movies.view.MoviesView;

public class MoviesPresenter extends Presenter<MoviesView> {

    private final MoviesCommands commands;

    MoviesPresenter(MoviesView view, MoviesCommands commands) {
        super(view);
        this.commands = commands;
    }

    @Override
    public void start() {
        GetTitleCommand getTitleCommand = commands.titleCommand();
        getTitleCommand.execute();
    }

    @Override
    public void stop() {

    }

    public void onMovieClicked(MovieModel movie, Navigator<MovieModel> toMovieNavigator) {
        toMovieNavigator.goTo(movie);
    }
}
