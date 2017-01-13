package com.idealista.android.sample.app.common.navigator;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movie.MovieActivity;

public class DetailNavigator extends Navigator<MovieModel> {

    DetailNavigator(Activity activity) {
        super(activity);
    }

    @NonNull
    @Override
    protected Intent getIntent(MovieModel movie) {
        Intent intent = new Intent(activity, MovieActivity.class);
        intent.putExtra(Extras.MOVIE_EXTRA, movie);
        return intent;
    }

}
