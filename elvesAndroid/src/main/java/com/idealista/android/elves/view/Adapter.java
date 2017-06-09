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

public class Adapter<VItemModel> extends RecyclerView.Adapter<Holder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private final CustomViewCreator<VItemModel> customViewCreator;
    private ListableModel<VItemModel> list;
    private final OnClicked<VItemModel> onClickListener;
    private CustomView headerCustomView;
    private CustomView footerCustomView;
    private boolean hasToShowHeader;
    private boolean hasToShowFooter;

    public Adapter(@NonNull final OnClicked<VItemModel> onClickListener, @NonNull final CustomViewCreator<VItemModel> customViewCreator) {
        this.customViewCreator = customViewCreator;
        this.onClickListener = onClickListener;
        this.list = new ListableModel<>();
    }

    private Adapter(@NonNull final OnClicked<VItemModel> onClickListener, @NonNull final CustomViewCreator<VItemModel> customViewCreator,
                   @NonNull final CustomView headerCustomView, @NonNull final CustomView footerCustomView) {
        this(onClickListener, customViewCreator);
        this.headerCustomView = headerCustomView;
        this.footerCustomView = footerCustomView;
        hasToShowHeader = true;
        hasToShowFooter = true;
    }

    public void add(@NonNull final ListableModel<VItemModel> listable) {
        this.list = listable;
        notifyDataSetChanged();
    }

    public void addFirst(@NonNull final ListableModel<VItemModel> listable) {
        list.addFirst(listable);
        notifyItemRangeInserted(0, listable.size() - 1);
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

    @Override public void onBindViewHolder(Holder holder, int position) {
        if (!isHeaderPosition(position) && !isFooterPosition(position)) {
            VItemModel customView = list.get(getUpdatedPosition(position));
            holder.bind(customView);
        }
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            CustomView<VItemModel> customView = customViewCreator.create(parent.getContext());
            customView.setOnClicked(onClickListener);
            return new Holder((View) customView);
        } else if (viewType == TYPE_HEADER) {
            if (headerCustomView == null) { throw new IllegalArgumentException("HeaderCustomView must not be null"); }
            return new Holder((View) headerCustomView);
        } else {
            if (footerCustomView == null) { throw new IllegalArgumentException("FooterCustomView must not be null"); }
            return new Holder((View) footerCustomView);
        }
    }

    public void clear() {
        add(new ListableModel<VItemModel>());
    }

    public ListableModel<VItemModel> getList() {
        return list.copy();
    }

    @Override public int getItemCount() {
        int itemCount = list.size();
        if (hasToShowHeader) {
            itemCount++;
        }

        if (hasToShowFooter) {
            itemCount++;
        }

        return itemCount;
    }

    @Override public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return TYPE_HEADER;
        } else if (isFooterPosition(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    public void setHeaderCustomView(final CustomView headerCustomView) {
        hideHeader();
        this.headerCustomView = headerCustomView;
        showHeader();
    }

    public void setFooterCustomView(final CustomView footerCustomView) {
        hideFooter();
        this.footerCustomView = footerCustomView;
        showFooter();
    }

    public void showHeader() {
        hasToShowHeader = true;
        notifyItemInserted(0);
    }

    public void showFooter() {
        hasToShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    public void hideHeader() {
        hasToShowHeader = false;
        notifyItemRemoved(0);
    }

    public void hideFooter() {
        hasToShowFooter = false;
        notifyItemRemoved(getItemCount());
    }

    private boolean isFooterPosition(final int position) {
        return hasToShowFooter && position == getItemCount() - 1;
    }

    private boolean isHeaderPosition(final int position) {
        return hasToShowHeader && position == 0;
    }

    private int getUpdatedPosition(int position) {
        return hasToShowHeader ? position - 1 : position;
    }

    public static class Builder<VItemModel> {

        private CustomViewCreator<VItemModel> customViewCreator;
        private OnClicked<VItemModel> onClickListener;
        private CustomView headerCustomView;
        private CustomView footerCustomView;

        public Builder setCustomViewCreator(final CustomViewCreator<VItemModel> creator) {
            if (creator == null) return this;
            this.customViewCreator = creator;
            return this;
        }

        public Builder setClickListener(final OnClicked<VItemModel> onClickListener) {
            if (onClickListener == null) return this;
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setHeaderCustomView(final CustomView headerCustomView) {
            if (headerCustomView == null) return this;
            this.headerCustomView = headerCustomView;
            return this;
        }

        public Builder setFooterCustomView(final CustomView footerCustomView) {
            if (footerCustomView == null) return this;
            this.footerCustomView = footerCustomView;
            return this;
        }

        public Adapter<VItemModel> build() {
            return new Adapter<>(onClickListener, customViewCreator, headerCustomView, footerCustomView);
        }
    }
}
