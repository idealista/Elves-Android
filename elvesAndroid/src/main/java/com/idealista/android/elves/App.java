package com.idealista.android.elves;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.idealista.android.elves.navigator.NavigatorFactory;

import java.util.concurrent.Executor;

public abstract class App extends Application {

    private static NavigatorFactory navigatorFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        navigatorFactory = new NavigatorFactory();
    }

    public static NavigatorFactory getNavigatorFactory() {
        return navigatorFactory;
    }

    protected Executor executor = new Executor() {

        private final Handler threadHandlerExecutor = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            threadHandlerExecutor.post(runnable);
        }

    };

}
