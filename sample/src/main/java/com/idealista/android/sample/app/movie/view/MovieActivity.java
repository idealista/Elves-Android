package com.idealista.android.sample.app.movie.view;


import android.os.Bundle;

import com.idealista.android.elvesandroid.navigator.view.mvp.Activity;
import com.idealista.android.sample.R;
import com.idealista.android.sample.app.common.navigator.Extras;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movie.presenter.MoviePresenter;

public class MovieActivity extends Activity<MoviePresenter> implements MovieView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie;
    }

    @Override
    public void prepare() {
        MovieModel movieModel = getIntent().getParcelableExtra(Extras.MOVIE_EXTRA);
        presenter.setMovie(movieModel);
    }

    @Override
    public void setTitle(String message) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(message);
        }
    }
}
