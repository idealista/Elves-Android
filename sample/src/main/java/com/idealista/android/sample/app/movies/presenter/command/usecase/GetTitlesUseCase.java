package com.idealista.android.sample.app.movies.presenter.command.usecase;


import com.idealista.android.sample.app.model.MoviesModel;
import com.idealista.android.usecase.UseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class GetTitlesUseCase extends UseCase<MoviesModel, List<String>> {

    @Override
    protected Callable<List<String>> build() {

        return new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> list = new ArrayList<>();
                list.add("Zootopia");
                list.add("Deadpool");
                list.add("Captain America: Civil War");
                list.add("Rogue One. A Star Wars story");
                list.add("The jungle book");
                list.add("Finding Dory");
                list.add("Doctor Strange");
                list.add("Suicide squad");
                return list;
            }
        };
    }
}
