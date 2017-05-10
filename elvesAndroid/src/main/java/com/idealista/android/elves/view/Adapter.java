package com.idealista.android.elves.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.idealista.android.elves.model.ListableModel;
import com.idealista.android.elves.view.mvp.view.CustomViewCreator;
import com.idealista.android.elves.view.widget.CustomView;
import com.idealista.android.elves.view.widget.OnClicked;
import java.util.Iterator;

public class Adapter<VItemModel> extends RecyclerView.Adapter<Adapter.Holder> {

    private final CustomViewCreator<VItemModel> customViewCreator;
    private ListableModel<VItemModel> list;
    private final OnClicked<VItemModel> onClickListener;

    public Adapter(@NonNull final OnClicked<VItemModel> onClickListener, @NonNull final CustomViewCreator<VItemModel> customViewCreator) {
        this.customViewCreator = customViewCreator;
        this.onClickListener = onClickListener;
        this.list = new ListableModel<>();
    }

    public void add(@NonNull final ListableModel<VItemModel> listable) {
        this.list = listable;
        notifyDataSetChanged();
    }

    public void add(@NonNull final VItemModel model) {
        int position = list.add(model);
        notifyItemInserted(position);
    }

    public void addAll(@NonNull final ListableModel<VItemModel> listable) {
        final Iterator<VItemModel> iterator = listable.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomView<VItemModel> customView = customViewCreator.create(parent.getContext());
        customView.setOnClicked(onClickListener);
        return new Holder((View) customView);
    }

    @Override public void onBindViewHolder(Adapter.Holder holder, int position) {
        VItemModel customView = list.get(position);
        holder.bind(customView);
    }

    @Override public int getItemCount() {
        return list.size();
    }

    public void clear() {
        add(new ListableModel<VItemModel>());
    }

    class Holder extends RecyclerView.ViewHolder {

        Holder(View itemView) {
            super(itemView);
        }

        void bind(VItemModel model) {
            ( (CustomView<VItemModel>) itemView ).render(model);
        }
    }
}
