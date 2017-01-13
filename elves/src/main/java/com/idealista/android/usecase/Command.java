package com.idealista.android.usecase;

import com.idealista.android.mvp.AutoStoppable;

public abstract class Command<TView> {

    protected final TView view;
    protected final AutoStoppable autoStoppable;

    public Command(TView view, AutoStoppable autoStoppable) {
        this.view = view;
        this.autoStoppable = autoStoppable;
    }

    public void execute() {
        if (autoStoppable != null) {
            autoStoppable.addCommand(this);
        }
    }

    public abstract void cancel();

}