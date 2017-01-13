package com.idealista.android.sample.app.movies.view;


import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idealista.android.sample.R;
import com.idealista.android.sample.app.model.MovieModel;

public class MovieView extends LinearLayout {

    private TextView textViewTitle;
    private OnMovieClickListener onMovieClickListener;

    public MovieView(Context context) {
        this(context, null);
    }

    public MovieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MovieView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void render(final MovieModel movie) {
        textViewTitle.setText(movie.getTitle());
        initOnClickListener(movie);
    }

    private void initOnClickListener(final MovieModel movie) {
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onClick(movie);
                }
            }
        });
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_movie, this, true);
        textViewTitle = (TextView) findViewById(R.id.tvTitle);
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public interface OnMovieClickListener {
        void onClick(final MovieModel movieModel);
    }
}
