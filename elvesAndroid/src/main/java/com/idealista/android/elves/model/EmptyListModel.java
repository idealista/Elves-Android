package com.idealista.android.elves.model;


public class EmptyListModel<VItemModel> implements ListableModel<VItemModel> {


    @Override
    public int add(VItemModel item) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public VItemModel get(int position) {
        return null;
    }
}
