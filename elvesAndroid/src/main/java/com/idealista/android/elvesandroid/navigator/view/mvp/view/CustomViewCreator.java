package com.idealista.android.elvesandroid.navigator.view.mvp.view;


import android.content.Context;

import com.idealista.android.view.mvp.view.CustomView;

public interface CustomViewCreator<TViewModel> {

    CustomView<TViewModel> create(Context context);

}
