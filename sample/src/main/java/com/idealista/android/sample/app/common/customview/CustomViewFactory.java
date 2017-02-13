package com.idealista.android.sample.app.common.customview;


import android.content.Context;

import com.idealista.android.elvesandroid.navigator.view.mvp.view.CustomView;
import com.idealista.android.elvesandroid.navigator.view.mvp.view.CustomViewCreator;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movies.view.MovieView;

public final class CustomViewFactory {

    public CustomViewCreator<MovieModel> getMovieViewCreator() {
        return new CustomViewCreator<MovieModel>() {
            @Override
            public CustomView<MovieModel> create(Context context) {
                return new MovieView(context);
            }
        };
    }

}
