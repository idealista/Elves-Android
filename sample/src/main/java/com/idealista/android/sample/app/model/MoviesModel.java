package com.idealista.android.sample.app.model;


import com.idealista.android.elves.business.Listable;

import java.util.ArrayList;
import java.util.List;

public class MoviesModel implements Listable<MovieModel> {

    private List<MovieModel> list;

    public MoviesModel() {
        this.list = new ArrayList<>();
    }

    @Override
    public int add(MovieModel movie) {
        list.add(movie);
        return list.indexOf(movie);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public MovieModel get(int position) {
        return list.get(position);
    }

}
