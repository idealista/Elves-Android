package com.idealista.android.usecase;

import com.idealista.android.tasks.CancellationToken;
import com.idealista.android.tasks.CancellationTokenSource;
import com.idealista.android.tasks.Continuation;
import com.idealista.android.tasks.Task;

import java.util.concurrent.Callable;

public abstract class UseCase<TViewObjects, VDomainObject> {

    private CancellationTokenSource cancellationTokenSource;

    public void execute(final UiCommand<VDomainObject, TViewObjects> command) {
        if (command == null) return;

        cancellationTokenSource = new CancellationTokenSource();
        CancellationToken cancellationToken = cancellationTokenSource.getToken();

        Task.callInBackground(build()).onSuccess(new Continuation<VDomainObject, TViewObjects>() {
            @Override
            public TViewObjects then(Task<VDomainObject> task) throws Exception {
                return command.background(task.getResult());
            }
        }, Task.BACKGROUND_EXECUTOR, cancellationToken).onSuccess(new Continuation<TViewObjects,
                Void>() {
            @Override
            public Void then(Task<TViewObjects> task) throws Exception {
                command.ui(task.getResult());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR, cancellationToken).continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(Task<Void> task) throws Exception {
                if (task.getError() != null) {
                    command.error(task.getError());
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR, cancellationToken);
    }

    public void execute() {
        cancellationTokenSource = new CancellationTokenSource();
        CancellationToken cancellationToken = cancellationTokenSource.getToken();
        Task.callInBackground(build(), cancellationToken);
    }

    public void cancel() {
        if (cancellationTokenSource != null) {
            cancellationTokenSource.cancel();
        }
    }

    protected abstract Callable<VDomainObject> build();

}
