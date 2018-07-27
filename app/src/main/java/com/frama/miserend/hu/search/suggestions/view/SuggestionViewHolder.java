package com.frama.miserend.hu.search.suggestions.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by maczak on 2018. 02. 15..
 */

public abstract class SuggestionViewHolder<T> extends RecyclerView.ViewHolder {

    private View root;

    public SuggestionViewHolder(View itemView) {
        super(itemView);
        root = itemView;
    }

    public View getRoot() {
        return root;
    }

    public abstract void bind(T data);

}
