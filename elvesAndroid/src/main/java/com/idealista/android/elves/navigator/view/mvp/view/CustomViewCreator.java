package com.idealista.android.elves.navigator.view.mvp.view;


import android.content.Context;

import com.idealista.android.elves.navigator.view.widget.CustomView;

public interface CustomViewCreator<TViewModel> {

    CustomView<TViewModel> create(Context context);

}
