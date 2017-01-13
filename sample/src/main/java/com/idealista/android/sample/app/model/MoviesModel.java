package com.idealista.android.sample.app.model;


import java.util.ArrayList;
import java.util.List;

public class MoviesModel {

    private List<MovieModel> list;

    public MoviesModel() {
        this.list = new ArrayList<>();
    }

    public int add(MovieModel movie) {
        list.add(movie);
        return list.indexOf(movie);
    }

    public int size() {
        return list.size();
    }

    public MovieModel get(int position) {
        return list.get(position);
    }

}
