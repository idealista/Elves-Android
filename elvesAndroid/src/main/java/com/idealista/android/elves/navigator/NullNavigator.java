package com.idealista.android.elves.navigator;

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
