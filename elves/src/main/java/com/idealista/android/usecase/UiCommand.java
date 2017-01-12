package com.idealista.android.usecase;

public interface UiCommand<VResults, TResults>  {

    void execute();

    void cancel();

    TResults background(VResults results);

    void ui(TResults result);

    void error(Exception error);

}