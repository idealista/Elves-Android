package com.idealista.android.elves.business;


public interface Listable<TModel> {

    int add(TModel item);

    int size();

    TModel get(int position);

    boolean isEmpty();
}
