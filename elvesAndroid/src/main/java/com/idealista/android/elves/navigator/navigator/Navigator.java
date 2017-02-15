package com.idealista.android.elves.navigator.navigator;

import android.app.Activity;
import android.content.Intent;

public abstract class Navigator<ViewModel> {

    protected final Activity activity;

    protected Navigator(Activity activity) {
        this.activity = activity;
    }

    public void goTo(ViewModel viewModel) {
        activity.startActivity(getIntent(viewModel));
    }

    protected abstract Intent getIntent(ViewModel viewModel);

}
