package com.idealista.android.sample.app.movies.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.idealista.android.elvesandroid.navigator.navigator.Navigator;
import com.idealista.android.elvesandroid.navigator.view.Adapter;
import com.idealista.android.elvesandroid.navigator.view.mvp.Activity;
import com.idealista.android.elvesandroid.navigator.view.mvp.view.CustomViewCreator;
import com.idealista.android.view.mvp.view.OnClicked;
import com.idealista.android.sample.R;
import com.idealista.android.sample.app.common.customview.CustomViewFactory;
import com.idealista.android.sample.app.common.navigator.DetailNavigator;
import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.sample.app.movies.presenter.MoviesPresenter;

public class MoviesActivity extends Activity<MoviesPresenter> implements MoviesView {

    private Adapter<MovieModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearMovies() {
        adapter.clear();
    }

    private OnClicked<MovieModel> onClickListener = new OnClicked<MovieModel>() {

        @Override
        public void onClick(MovieModel movieModel) {
            notifyPresenterMovieClicked(movieModel);
        }
    };

    private void notifyPresenterMovieClicked(MovieModel movieModel) {
        Navigator<MovieModel> navigatorMovie = getNavigator(this, DetailNavigator.class);
        presenter.onMovieClicked(movieModel, navigatorMovie);
    }

    @Override
    public void addMovies(MoviesModel movies) {
        adapter.add(movies);
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
        CustomViewCreator<MovieModel> customViewCreator = new CustomViewFactory().getMovieViewCreator();
        adapter = new Adapter<>(onClickListener, customViewCreator);
        recyclerView.setAdapter(adapter);
    }
}
