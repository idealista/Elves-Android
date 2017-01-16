package com.idealista.android.elvesandroid.navigator.navigator;

import android.content.Intent;

public class NullNavigator extends Navigator {

    protected NullNavigator() {
        super(null);
    }

    @Override
    protected Intent getIntent(Object o) {
        return null;
    }

    @Override
    public void goTo(Object o) {
        // Nothing to do here
    }
}
