package com.idealista.android.elves.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ListableModel<VItemModel> {

    protected final List<VItemModel> list;

    public ListableModel() {
        this.list = new ArrayList<>();
    }

    public ListableModel(List<VItemModel> list) {
        if (list != null) {
            this.list = new ArrayList<>(list);
        } else {
            this.list = new ArrayList<>();
        }
    }

    public int add(@NonNull final VItemModel item) {
        list.add(item);
        return list.indexOf(item);
    }

    public int size() {
        return list.size();
    }

    public VItemModel get(final int position) {
        return list.get(position);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Iterator<VItemModel> iterator() {
        return list.iterator();
    }

    public ListableModel<VItemModel> copy() {
        return new ListableModel<>(list);
    }

    @Nullable public VItemModel getFirst() {
        try {
            return get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Nullable public VItemModel getFirst(Comparator<VItemModel> comparator) {
        sort(comparator);
        return getFirst();
    }

    public void sort(Comparator<VItemModel> comparator) {
        Collections.sort(list, comparator);
    }
}
