package com.idealista.android.sample.app;

import com.idealista.android.elves.App;
import com.idealista.android.elves.tasks.AndroidExecutors;

public class MoviesApplication extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidExecutors.UIThreadExecutor.init(executor);
    }

}
