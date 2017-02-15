package com.idealista.android.elves.business;


public interface Listable<TModel> {

    int add(TModel movie);

    int size();

    TModel get(int position);
}
