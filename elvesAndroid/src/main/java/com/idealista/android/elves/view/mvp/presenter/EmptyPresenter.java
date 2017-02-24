package com.idealista.android.elves.view.mvp.presenter;


import android.support.annotation.NonNull;

import com.idealista.android.elves.view.mvp.view.View;

public class EmptyPresenter extends Presenter<View> {

    public EmptyPresenter(@NonNull final View view) {
        super(view);
    }

    @Override
    public void start() {

    }
}
