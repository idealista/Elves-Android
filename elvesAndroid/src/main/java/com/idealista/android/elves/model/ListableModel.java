package com.idealista.android.elves.model;


import android.support.annotation.NonNull;

public interface ListableModel<TModel> {

    int add(@NonNull final TModel item);

    int size();

    TModel get(final int position);

    boolean isEmpty();

}
