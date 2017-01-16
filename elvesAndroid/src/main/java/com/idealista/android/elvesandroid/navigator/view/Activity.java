package com.idealista.android.elvesandroid.navigator.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idealista.android.mvp.Presenter;
import com.idealista.android.mvp.View;

public abstract class Activity<P extends Presenter> extends AppCompatActivity implements View {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        prepare();
        presenter = getPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    private P getPresenter() {
        PresenterFactory<P> presenterFactory = getPresenterFactory();
        return presenterFactory.getPresenter(this);
    }

    public abstract PresenterFactory<P> getPresenterFactory();

    public abstract int getLayoutId();

    public abstract void prepare();
}
