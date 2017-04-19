package com.idealista.android.elves.business;


public class EmptyList<V> implements Listable<V> {

    @Override
    public int add(V item) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V get(int position) {
        return null;
    }

    @Override public boolean isEmpty() {
        return true;
    }
}
