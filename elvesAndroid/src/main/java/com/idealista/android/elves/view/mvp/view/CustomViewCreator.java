package com.idealista.android.elves.view.mvp.view;


import android.content.Context;
import android.support.annotation.NonNull;

import com.idealista.android.elves.view.widget.CustomView;

public interface CustomViewCreator<TViewModel> {

    CustomView<TViewModel> create(@NonNull final Context context);

}
