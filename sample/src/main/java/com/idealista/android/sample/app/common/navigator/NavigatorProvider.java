package com.idealista.android.sample.app.common.navigator;


import android.app.Activity;

public class NavigatorProvider {

    private final Activity activity;

    public NavigatorProvider(Activity activity) {
        this.activity = activity;
    }

    public DetailNavigator getDetailNavigator() {
        return new DetailNavigator(activity);
    }

}
