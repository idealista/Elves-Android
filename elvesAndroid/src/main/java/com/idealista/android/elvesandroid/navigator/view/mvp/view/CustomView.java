package com.idealista.android.elvesandroid.navigator.view.mvp.view;

public interface CustomView<TViewModel> {

    void prepare();

    int getLayoutId();

    void setOnClicked(OnClicked<TViewModel> viewModel);

    void render(TViewModel viewModel);
}
