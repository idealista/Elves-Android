package com.idealista.android.business;


public interface Listable<TModel> {

    int add(TModel movie);

    int size();

    TModel get(int position);
}
