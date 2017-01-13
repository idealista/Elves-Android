package com.idealista.android.sample.app.movies.view;


import com.idealista.android.mvp.View;
import com.idealista.android.sample.app.model.MoviesModel;

public interface MainView extends View {

    void showMessage(String message);

    void clearMovies();

    void addMovies(MoviesModel movies);
}
