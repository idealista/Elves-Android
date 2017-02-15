package com.idealista.android.elves.navigator.view.mvp.presenter;

import com.idealista.android.elves.navigator.view.mvp.view.View;

public interface PresenterFactory<P extends Presenter> {

    P getPresenter(View view);

}
