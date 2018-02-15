package com.frama.miserend.hu.search.suggestions;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by maczak on 2018. 02. 15..
 */

public abstract class SuggestionViewHolder<T> extends RecyclerView.ViewHolder {
    public SuggestionViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T data);

}
