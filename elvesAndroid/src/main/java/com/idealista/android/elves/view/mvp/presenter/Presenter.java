package com.idealista.android.elves.view.mvp.presenter;

import com.idealista.android.elves.view.mvp.view.View;

public abstract class Presenter<V extends View>{

    protected final V view;

    public Presenter(V view) {
        this.view = view;
    }

    public abstract void start();

    public void stop() {

    }
}
