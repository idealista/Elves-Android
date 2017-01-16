package com.idealista.android.elvesandroid.navigator.view;

import com.idealista.android.mvp.Presenter;
import com.idealista.android.mvp.View;

public interface PresenterFactory<P extends Presenter> {

    P getPresenter(View view);

}
