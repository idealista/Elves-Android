package com.idealista.android.elvesandroid.navigator.view.widget;


import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.idealista.android.elvesandroid.navigator.view.mvp.view.CustomView;

public abstract class RelativeView<TViewModel> extends RelativeLayout implements CustomView<TViewModel> {

    public RelativeView(Context context) {
        this(context, null);
    }

    public RelativeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RelativeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutId(), this, true);
        prepare();
    }
}
