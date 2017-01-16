package com.idealista.android.sample.app.movies.presenter;


import com.idealista.android.mvp.Presenter;
import com.idealista.android.sample.app.common.navigator.Navigator;
import com.idealista.android.sample.app.common.navigator.NavigatorProvider;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movies.presenter.command.GetTitleCommand;
import com.idealista.android.sample.app.movies.view.MoviesView;

public class MainPresenter extends Presenter<MoviesView> {

    private NavigatorProvider navigatorProvider;
    private GetTitleCommand getTitleCommand;

    public MainPresenter(MoviesView view) {
        super(view);
    }

    public void setNavigatorProvider(NavigatorProvider navigatorProvider) {
        this.navigatorProvider = navigatorProvider;
    }

    @Override
    public void start() {
        getTitleCommand = new GetTitleCommand(view, this);
        getTitleCommand.execute();
    }

    public void stop() {

    }

    public void onMovieClicked(MovieModel movie) {
        Navigator<MovieModel> navigator = navigatorProvider.getDetailNavigator();
        navigator.goTo(movie);
    }
}
