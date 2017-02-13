package com.idealista.android.sample.app.movies.view;


import com.idealista.android.view.mvp.View;
import com.idealista.android.sample.app.model.MoviesModel;

public interface MoviesView extends View {

    void showMessage(String message);

    void clearMovies();

    void addMovies(MoviesModel movies);
}
