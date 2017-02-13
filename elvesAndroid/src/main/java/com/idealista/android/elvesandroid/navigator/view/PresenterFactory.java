package com.idealista.android.elvesandroid.navigator.view;

import com.idealista.android.elvesandroid.navigator.view.mvp.Presenter;
import com.idealista.android.elvesandroid.navigator.view.mvp.View;

public interface PresenterFactory<P extends Presenter> {

    P getPresenter(View view);

}
