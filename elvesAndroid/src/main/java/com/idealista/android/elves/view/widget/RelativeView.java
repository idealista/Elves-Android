package com.idealista.android.elves.view.widget;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public abstract class RelativeView<TViewModel> extends RelativeLayout implements CustomView<TViewModel> {

    public RelativeView(@NonNull final Context context) {
        this(context, null);
    }

    public RelativeView(@NonNull final Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeView(@NonNull final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RelativeView(@NonNull final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutId(), this, true);
        prepare();
    }
}
