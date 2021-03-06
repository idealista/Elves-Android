package com.idealista.android.sample.app.common.customview;


import android.content.Context;
import android.support.annotation.NonNull;

import com.idealista.android.elves.view.mvp.view.CustomViewCreator;
import com.idealista.android.elves.view.widget.CustomView;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.movies.view.FooterMovieView;
import com.idealista.android.sample.app.movies.view.HeaderMovieView;
import com.idealista.android.sample.app.movies.view.LoadingView;
import com.idealista.android.sample.app.movies.view.MovieView;

public final class CustomViewFactory {

    public CustomViewCreator<MovieModel> getMovieViewCreator() {
        return new CustomViewCreator<MovieModel>() {
            @Override
            public CustomView<MovieModel> create(@NonNull Context context) {
                return new MovieView(context);
            }
        };
    }

}
