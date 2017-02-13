package com.idealista.android.elvesandroid.navigator.view.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idealista.android.elvesandroid.navigator.App;
import com.idealista.android.elvesandroid.navigator.navigator.Navigator;
import com.idealista.android.elvesandroid.navigator.navigator.NavigatorFactory;
import com.idealista.android.elvesandroid.navigator.view.PresenterFactory;

public abstract class Activity<P extends Presenter> extends AppCompatActivity implements View {

    protected P presenter;
    protected NavigatorFactory navigatorFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        navigatorFactory = App.getNavigatorFactory();
        PresenterFactory presenterFactory = App.getPresenterFactory();
        presenter = (P) presenterFactory.getPresenter(this);
        prepare();
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

    protected Navigator getNavigator(Activity activity, Class clazz) {
        return navigatorFactory.getNavigator(activity, clazz);
    }

    public abstract int getLayoutId();

    public abstract void prepare();
}
