package com.idealista.android.elves.usecase;

public abstract class Command<TView> {

    protected final TView view;

    public Command(TView view) {
        this.view = view;
    }

    public abstract void cancel();

}