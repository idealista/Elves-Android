package com.idealista.android.sample.app.model;


import android.support.annotation.NonNull;

import com.idealista.android.elves.model.ListableModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesModel implements ListableModel<MovieModel> {

    private List<MovieModel> list;

    public MoviesModel() {
        this.list = new ArrayList<>();
    }

    @Override
    public int add(@NonNull MovieModel movie) {
        list.add(movie);
        return list.indexOf(movie);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public MovieModel get(@NonNull int position) {
        return list.get(position);
    }

}
