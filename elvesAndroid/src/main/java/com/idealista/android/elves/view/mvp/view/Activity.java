package com.idealista.android.elves.view.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idealista.android.elves.App;
import com.idealista.android.elves.navigator.Navigator;
import com.idealista.android.elves.navigator.NavigatorFactory;
import com.idealista.android.elves.view.mvp.presenter.Presenter;

public abstract class Activity<P extends Presenter, ViewModel> extends AppCompatActivity implements View {

    protected P presenter;
    protected NavigatorFactory<ViewModel> navigatorFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        navigatorFactory = App.getNavigatorFactory();
        presenter = getPresenter();
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

    protected Navigator<ViewModel> getNavigator(Activity activity, Class clazz) {
        return navigatorFactory.getNavigator(activity, clazz);
    }

    public abstract int getLayoutId();

    public abstract void prepare();

    public abstract P getPresenter();

}
