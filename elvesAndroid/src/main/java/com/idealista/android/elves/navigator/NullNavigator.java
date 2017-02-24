package com.idealista.android.elves.navigator;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

public class NullNavigator extends Navigator {

    NullNavigator(Activity activity) {
        super(activity);
    }

    @Override
    protected Intent getIntent(@NonNull final Object o) {
        return new Intent();
    }

    @Override
    public void goTo(@NonNull final Object o) {
        // Nothing to do here
    }
}
