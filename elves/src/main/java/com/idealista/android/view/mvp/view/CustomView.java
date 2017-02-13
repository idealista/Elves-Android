package com.idealista.android.view.mvp.view;

public interface CustomView<TViewModel> {

    void prepare();

    int getLayoutId();

    void setOnClicked(OnClicked<TViewModel> viewModel);

    void render(TViewModel viewModel);
}
