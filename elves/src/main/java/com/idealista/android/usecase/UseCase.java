package com.idealista.android.usecase;

interface UseCase<T, R> {

    void execute();

    void execute(UiCommand<T, R> command);

    void cancel();
}
