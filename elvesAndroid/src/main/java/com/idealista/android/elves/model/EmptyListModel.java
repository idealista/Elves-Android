package com.idealista.android.elves.model;


import android.support.annotation.NonNull;

public class EmptyListModel<VItemModel> implements ListableModel<VItemModel> {

    @Override
    public int add(@NonNull final VItemModel item) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public VItemModel get(final int position) {
        return null;
    }

    @Override public boolean isEmpty() {
        return true;
    }
}
