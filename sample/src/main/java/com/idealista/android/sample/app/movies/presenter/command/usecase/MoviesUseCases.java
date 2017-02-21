package com.idealista.android.sample.app.movies.presenter.command.usecase;


public class MoviesUseCases {

    public GetTitlesUseCase titles(){
        return new GetTitlesUseCase();
    }

}
