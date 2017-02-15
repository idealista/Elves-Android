package com.idealista.android.elves.navigator.view.mvp.presenter;

import com.idealista.android.elves.usecase.Command;
import com.idealista.android.elves.navigator.view.mvp.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class Presenter<V extends View>{

    protected final V view;
    private List<Command> commands;
    private final Object lock = new Object();

    public Presenter(V view) {
        this.view = view;
        commands = new ArrayList<>();
    }

    private void cancelCommands() {
        for (Command command : commands) {
            command.cancel();
        }
    }

    public void addCommand(Command command) {
        synchronized (lock) {
            commands.add(command);
        }
    }

    public abstract void start();

    public void stop() {
        cancelCommands();
    }
}
