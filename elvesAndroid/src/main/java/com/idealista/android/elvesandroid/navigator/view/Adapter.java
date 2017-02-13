package com.idealista.android.elvesandroid.navigator.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.idealista.android.business.EmptyList;
import com.idealista.android.business.Listable;
import com.idealista.android.elvesandroid.navigator.view.mvp.view.CustomView;
import com.idealista.android.elvesandroid.navigator.view.mvp.view.CustomViewCreator;
import com.idealista.android.elvesandroid.navigator.view.mvp.view.OnClicked;

public class Adapter<VItemModel> extends RecyclerView.Adapter<Adapter.Holder> {

    private final CustomViewCreator<VItemModel> customViewCreator;
    private Listable<VItemModel> list;
    private final OnClicked<VItemModel> onClickListener;

    public Adapter(OnClicked<VItemModel> onClickListener, CustomViewCreator<VItemModel> customViewCreator) {
        this.customViewCreator = customViewCreator;
        this.onClickListener = onClickListener;
        this.list = new EmptyList<>();
    }

    public void add(Listable<VItemModel> listable) {
        this.list = listable;
        notifyDataSetChanged();
    }

    public void add(VItemModel movie) {
        int position = list.add(movie);
        notifyItemInserted(position);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomView<VItemModel> customView = customViewCreator.create(parent.getContext());
        customView.setOnClicked(onClickListener);
        return new Holder((View) customView);
    }

    @Override
    public void onBindViewHolder(Adapter.Holder holder, int position) {
        VItemModel customView = list.get(position);
        holder.bind(customView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        add(new EmptyList<VItemModel>());
    }

    class Holder extends RecyclerView.ViewHolder {

        Holder(View itemView) {
            super(itemView);
        }

        void bind(VItemModel model) {
            ((CustomView<VItemModel>) itemView).render(model);
        }

    }

}
