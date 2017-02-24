package com.idealista.android.elves.navigator;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class Navigator<ViewModel> {

    protected final Activity activity;

    protected Navigator(@NonNull final Activity activity) {
        this.activity = activity;
    }

    public void goTo(@NonNull final ViewModel viewModel) {
        activity.startActivity(getIntent(viewModel));
    }

    protected abstract Intent getIntent(@NonNull final ViewModel viewModel);

}
