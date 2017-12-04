package com.idealista.android.elves.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.idealista.android.elves.view.widget.CustomView;

public class Holder<VItemModel> extends RecyclerView.ViewHolder {

    public Holder(View itemView) {
        super(itemView);
    }

    public void bind(VItemModel model) {
        ( (CustomView<VItemModel>) itemView ).render(model);
    }
}