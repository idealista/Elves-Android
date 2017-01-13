package com.idealista.android.sample.app.movies.presenter;


import com.idealista.android.mvp.Presenter;
import com.idealista.android.sample.app.common.navigator.DetailNavigator;
import com.idealista.android.sample.app.common.navigator.NavigatorProvider;
import com.idealista.android.sample.app.movies.presenter.command.GetTitleCommand;
import com.idealista.android.sample.app.movies.view.MainView;
import com.idealista.android.sample.app.model.MovieModel;

public class MainPresenter extends Presenter<MainView> {

    private NavigatorProvider navigatorProvider;
    private GetTitleCommand getTitleCommand;

    public MainPresenter(MainView view) {
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
        DetailNavigator navigator = navigatorProvider.getDetailNavigator();
        navigator.goTo(movie);
    }
}
