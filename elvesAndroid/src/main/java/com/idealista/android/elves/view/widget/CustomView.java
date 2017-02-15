package com.idealista.android.elves.view.widget;

public interface CustomView<TViewModel> {

    void prepare();

    int getLayoutId();

    void setOnClicked(OnClicked<TViewModel> viewModel);

    void render(TViewModel viewModel);
}
