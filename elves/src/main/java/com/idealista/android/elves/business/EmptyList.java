package com.idealista.android.elves.business;


public class EmptyList<V> implements Listable<V> {

    @Override
    public int add(V movie) {
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
}
