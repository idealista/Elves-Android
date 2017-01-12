package com.idealista.android.mvp;


import com.idealista.android.usecase.UiCommand;

interface CommandQueue {

    void addCommand(UiCommand command);

}
