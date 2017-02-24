package com.idealista.android.elves.view.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.idealista.android.elves.view.mvp.presenter.Presenter;

public abstract class Fragment<P extends Presenter> extends android.support.v4.app.Fragment implements View {

    protected P presenter;

    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        android.view.View view = inflater.inflate(getLayoutId(), container, false);
        prepare(view);
        presenter = getPresenter();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    public abstract int getLayoutId();

    public abstract void prepare(android.view.View view);

    public abstract P getPresenter();

}
