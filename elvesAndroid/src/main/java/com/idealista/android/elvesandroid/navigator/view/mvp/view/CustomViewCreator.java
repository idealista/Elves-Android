package com.idealista.android.elvesandroid.navigator.view.mvp.view;


import android.content.Context;

public interface CustomViewCreator<TViewModel> {

    CustomView<TViewModel> create(Context context);

}
