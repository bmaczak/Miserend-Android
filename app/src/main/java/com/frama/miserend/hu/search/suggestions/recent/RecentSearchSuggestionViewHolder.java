package com.frama.miserend.hu.search.suggestions.recent;

import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.search.suggestions.view.SuggestionViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maczak on 2018. 02. 15..
 */

public class RecentSearchSuggestionViewHolder extends SuggestionViewHolder<String> {

    @BindView(R.id.suggestion_recent_search_term)
    TextView searchTerm;

    public RecentSearchSuggestionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(String data) {
        searchTerm.setText(data);
    }
}
