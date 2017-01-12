package com.idealista.android.mvp;

import com.idealista.android.usecase.UiCommand;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePresenter<V extends BaseView> implements CommandQueue {

    protected final V view;
    protected List<UiCommand> commands;
    private final Object lock = new Object();

    public BasePresenter(V view, CommandQueue queue) {
        this.view = view;
        commands = new ArrayList<>();
    }

    public void finish() {
        cancelCommands();
    }

    public abstract void start();

    private void cancelCommands() {
        commands.forEach(UiCommand::cancel);
    }

    @Override
    public void addCommand(UiCommand command) {
        synchronized (lock) {
            commands.add(command);
        }
    }
}
