package com.idealista.android.sample.app.movies.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.idealista.android.elves.view.widget.LinearView;
import com.idealista.android.elves.view.widget.OnClicked;
import com.idealista.android.sample.R;

public class LoadingView extends LinearView<Void> {

    private ProgressBar progressBar;

    public LoadingView(@NonNull final Context context) {
        super(context);
    }

    public LoadingView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(@NonNull final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override public void prepare() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
    }

    @Override public int getLayoutId() {
        return R.layout.view_loading;
    }

    @Override public void setOnClicked(@NonNull final OnClicked<Void> onClicked) {

    }

    @Override public void render(@NonNull final Void aVoid) {

    }
}
