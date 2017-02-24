package com.idealista.android.elves.view.widget;

import android.support.annotation.NonNull;

public interface OnClicked<TViewModel> {

    void onClick(@NonNull final TViewModel item);

}
