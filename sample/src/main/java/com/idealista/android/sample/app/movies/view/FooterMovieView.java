package com.idealista.android.sample.app.movies.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.idealista.android.elves.view.widget.LinearView;
import com.idealista.android.elves.view.widget.OnClicked;
import com.idealista.android.sample.R;

public class FooterMovieView extends LinearView<Void> {

    private TextView textViewTitle;
    private OnClicked<Void> onClicked;

    public FooterMovieView(Context context) {
        super(context);
    }

    public FooterMovieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterMovieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FooterMovieView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void render(@NonNull final Void aVoid) {
    }

    @Override
    public void prepare() {
        textViewTitle = (TextView) findViewById(R.id.tvTitle);
        textViewTitle.setText("Footer ** Idealista **");
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_movie;
    }

    @Override
    public void setOnClicked(@NonNull OnClicked<Void> onClicked) {
        this.onClicked = onClicked;
    }


}
