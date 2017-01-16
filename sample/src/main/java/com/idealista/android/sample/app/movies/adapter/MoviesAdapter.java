package com.idealista.android.sample.app.movies.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.idealista.android.sample.app.model.MovieModel;
import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.sample.app.movies.view.MovieView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private MoviesModel moviesModel;
    private final MovieView.OnClicked<MovieModel> onClickListener;

    public MoviesAdapter(MovieView.OnClicked<MovieModel> onClickListener) {
        this.onClickListener = onClickListener;
        this.moviesModel = new MoviesModel();
    }


    public void add(MoviesModel movies) {
        this.moviesModel = movies;
        notifyDataSetChanged();
    }

    public void add(MovieModel movie) {
        int position = moviesModel.add(movie);
        notifyItemInserted(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieView movieView = new MovieView(parent.getContext());
        movieView.setOnClicked(onClickListener);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movie = moviesModel.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return moviesModel.size();
    }

    public void clear() {
        add(new MoviesModel());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        void bind(MovieModel movie) {
            ((MovieView) itemView).render(movie);
        }

    }
}
