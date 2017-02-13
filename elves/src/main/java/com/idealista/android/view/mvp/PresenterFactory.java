package com.idealista.android.view.mvp;

public interface PresenterFactory<P extends Presenter> {

    P getPresenter(View view);

}
