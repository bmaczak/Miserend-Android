package com.frama.miserend.hu.search.suggestions;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.List;

/**
 * Created by maczak on 2018. 02. 15..
 */

public class CustomSuggestionAdapter extends SuggestionsAdapter<Suggestion, SuggestionViewHolder> {

    private static final int TYPE_CITY = 0;


    public CustomSuggestionAdapter(LayoutInflater inflater, List<Suggestion> suggestions) {
        super(inflater);
        this.suggestions = suggestions;
    }

    @Override
    public void onBindSuggestionHolder(Suggestion suggestion, SuggestionViewHolder holder, int position) {
        holder.bind(suggestion.getData());
    }

    @Override
    public int getSingleViewHeight() {
        return 62;
    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CITY) {
            return new CitySuggestionViewHolder(getLayoutInflater().inflate(R.layout.item_suggestion_city, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (suggestions.get(position) instanceof CitySuggestion) {
            return TYPE_CITY;
        } else {
            return TYPE_CITY;
        }
    }
}
