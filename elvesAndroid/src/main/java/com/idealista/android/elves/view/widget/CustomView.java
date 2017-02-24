package com.idealista.android.elves.view.widget;

import android.support.annotation.NonNull;

public interface CustomView<TViewModel> {

    void prepare();

    int getLayoutId();

    void setOnClicked(@NonNull final OnClicked<TViewModel> onClicked);

    void render(@NonNull final TViewModel viewModel);
}
