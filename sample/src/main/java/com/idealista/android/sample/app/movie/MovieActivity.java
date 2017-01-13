package com.idealista.android.sample.app.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idealista.android.sample.R;
import com.idealista.android.sample.app.common.navigator.Extras;
import com.idealista.android.sample.app.model.MovieModel;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        MovieModel movieModel = getIntent().getParcelableExtra(Extras.MOVIE_EXTRA);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movieModel.getTitle());
        }

    }
}
