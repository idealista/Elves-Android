package com.idealista.android.sample.app.movies.presenter;

import com.idealista.android.elvesandroid.navigator.navigator.Navigator;
import com.idealista.android.elvesandroid.navigator.view.mvp.Presenter;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movies.presenter.command.GetTitleCommand;
import com.idealista.android.sample.app.movies.view.MoviesView;

public class MoviesPresenter extends Presenter<MoviesView> {

    public MoviesPresenter(MoviesView view) {
        super(view);
    }

    @Override
    public void start() {
        GetTitleCommand getTitleCommand = new GetTitleCommand(view, this);
        getTitleCommand.execute();
    }

    @Override
    public void stop() {

    }

    public void onMovieClicked(MovieModel movie, Navigator<MovieModel> navigator) {
        navigator.goTo(movie);
    }
}
