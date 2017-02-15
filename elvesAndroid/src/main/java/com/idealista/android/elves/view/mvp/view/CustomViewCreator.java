package com.idealista.android.elves.view.mvp.view;


import android.content.Context;

import com.idealista.android.elves.view.widget.CustomView;

public interface CustomViewCreator<TViewModel> {

    CustomView<TViewModel> create(Context context);

}
