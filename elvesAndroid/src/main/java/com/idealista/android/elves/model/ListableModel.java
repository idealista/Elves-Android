package com.idealista.android.elves.model;


public interface ListableModel<TModel> {

    int add(TModel item);

    int size();

    TModel get(int position);
}
