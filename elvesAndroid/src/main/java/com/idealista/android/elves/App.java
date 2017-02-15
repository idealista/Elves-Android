package com.idealista.android.elves;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.idealista.android.elves.navigator.NavigatorFactory;
import com.idealista.android.elves.view.mvp.presenter.PresenterFactory;

import java.util.concurrent.Executor;

public abstract class App extends Application {

    private static PresenterFactory presenterFactory;
    private static NavigatorFactory navigatorFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        presenterFactory = createPresenterFactory();
        navigatorFactory = new NavigatorFactory();
    }

    public static PresenterFactory getPresenterFactory() {
        return presenterFactory;
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

    protected abstract PresenterFactory createPresenterFactory();

}
