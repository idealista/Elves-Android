package com.idealista.android.sample.app.movies.view;


import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.idealista.android.mvp.view.CustomView;

public abstract class LinearView<TViewModel> extends LinearLayout implements CustomView {

    public LinearView(Context context) {
        this(context, null);
    }

    public LinearView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutId(), this, true);
        prepare();
    }

    public abstract void render(TViewModel viewModel);

    public interface OnMovieClickListener<TViewModel> {
        void onClick(final TViewModel movieModel);
    }
}
