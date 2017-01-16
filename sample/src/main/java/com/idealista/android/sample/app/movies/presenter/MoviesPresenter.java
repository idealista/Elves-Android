package com.idealista.android.sample.app.movies.presenter;


import com.idealista.android.elvesandroid.navigator.Navigator;
import com.idealista.android.mvp.Presenter;
import com.idealista.android.sample.app.common.navigator.NavigatorProvider;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movies.presenter.command.GetTitleCommand;
import com.idealista.android.sample.app.movies.view.MoviesView;

public class MoviesPresenter extends Presenter<MoviesView> {

    private NavigatorProvider navigatorProvider;

    public MoviesPresenter(MoviesView view) {
        super(view);
    }

    public void setNavigatorProvider(NavigatorProvider navigatorProvider) {
        this.navigatorProvider = navigatorProvider;
    }

    @Override
    public void start() {
        GetTitleCommand getTitleCommand = new GetTitleCommand(view, this);
        getTitleCommand.execute();
    }

    @Override
    public void stop() {

    }

    public void onMovieClicked(MovieModel movie) {
        Navigator<MovieModel> navigator = navigatorProvider.getDetailNavigator();
        navigator.goTo(movie);
    }
}
