package com.idealista.android.sample.app.movies.view;


import com.idealista.android.elves.navigator.view.mvp.view.View;
import com.idealista.android.sample.app.model.MoviesModel;

public interface MoviesView extends View {

    void showMessage(String message);

    void clearMovies();

    void addMovies(MoviesModel movies);
}
