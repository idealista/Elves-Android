package com.idealista.android.sample;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.idealista.android.tasks.AndroidExecutors;

import java.util.concurrent.Executor;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidExecutors.UIThreadExecutor.init(executor);
    }


    private Executor executor = new Executor() {

        private final Handler threadHandlerExecutor = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            threadHandlerExecutor.post(runnable);
        }

    };

}
