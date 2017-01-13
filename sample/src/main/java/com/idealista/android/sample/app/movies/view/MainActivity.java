package com.idealista.android.sample.app.movies.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.idealista.android.sample.R;
import com.idealista.android.sample.app.common.Activity;
import com.idealista.android.sample.app.common.navigator.NavigatorProvider;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.sample.app.movies.presenter.MainPresenter;

public class MainActivity extends Activity<MainPresenter> implements MainView {

    private MainMovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setNavigatorProvider(new NavigatorProvider(this));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearMovies() {
        movieAdapter.clear();
    }

    private MovieView.OnMovieClickListener onMovieClickListener = new MovieView.OnMovieClickListener() {
        @Override
        public void onClick(MovieModel movie) {
            presenter.onMovieClicked(movie);
        }
    };

    @Override
    public void addMovies(MoviesModel movies) {
        movieAdapter.add(movies);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void prepare() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager =
                new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        movieAdapter = new MainMovieAdapter(onMovieClickListener);
        recyclerView.setAdapter(movieAdapter);
    }
}
