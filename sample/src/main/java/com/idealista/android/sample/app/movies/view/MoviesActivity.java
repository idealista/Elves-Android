package com.idealista.android.sample.app.movies.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.idealista.android.elves.business.Listable;
import com.idealista.android.elves.navigator.Navigator;
import com.idealista.android.elves.view.Adapter;
import com.idealista.android.elves.view.mvp.view.Activity;
import com.idealista.android.elves.view.widget.OnClicked;
import com.idealista.android.sample.R;
import com.idealista.android.sample.app.common.customview.CustomViewFactory;
import com.idealista.android.sample.app.common.navigator.DetailNavigator;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.sample.app.movies.presenter.GetMoviesPresenter;
import com.idealista.android.sample.app.movies.presenter.MoviesPresenter;

public class MoviesActivity extends Activity<MoviesPresenter, MovieModel> implements MoviesView {

    private Adapter<MovieModel> adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override public void clearMovies() {
        adapter.clear();
    }

    private OnClicked<MovieModel> onClickListener = new OnClicked<MovieModel>() {
        @Override public void onClick(@NonNull MovieModel movieModel) {
            notifyPresenterMovieClicked(movieModel);
        }
    };

    private void notifyPresenterMovieClicked(MovieModel movieModel) {
        Navigator<MovieModel> navigatorMovie = getNavigator(this, DetailNavigator.class);
        presenter.onMovieClicked(movieModel, navigatorMovie);
    }

    @Override public void addMovies(MoviesModel movies) {
        adapter.add(movies);
    }

    @Override public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override public void prepare() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        final CustomViewFactory customViewFactory = new CustomViewFactory();
        adapter = new Adapter.Builder<MovieModel>().setClickListener(onClickListener)
                                         .setCustomViewCreator(customViewFactory.getMovieViewCreator())
                                         .setHeaderCustomView(new HeaderMovieView(getBaseContext()))
                                         .setFooterCustomView(new FooterMovieView(getBaseContext()))
                                         .build();

        recyclerView.setAdapter(adapter);

        adapter.showHeader();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override public void run() {
                final MoviesModel movies = new MoviesModel();
                movies.add(new MovieModel("Idealista"));
                movies.add(new MovieModel("Idealista"));
                movies.add(new MovieModel("Idealista"));
                movies.add(new MovieModel("Idealista"));
                adapter.addFirst(movies);
            }
        }, 5000);
    }

    @Override public MoviesPresenter getPresenter() {
        return new GetMoviesPresenter(this).get();
    }
}
