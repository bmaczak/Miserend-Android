package com.frama.miserend.hu.search.suggestions.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.search.searchbar.CustomSearchBar;
import com.frama.miserend.hu.search.suggestions.Suggestion;
import com.frama.miserend.hu.search.suggestions.advanced.AdvancedSearchSuggestion;
import com.frama.miserend.hu.search.suggestions.advanced.AdvancedSearchSuggestionViewHolder;
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestion;
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestionViewHolder;
import com.frama.miserend.hu.search.suggestions.city.CitySuggestion;
import com.frama.miserend.hu.search.suggestions.city.CitySuggestionViewHolder;
import com.frama.miserend.hu.search.suggestions.recent.RecentSearchSuggestionViewHolder;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

/**
 * Created by maczak on 2018. 02. 15..
 */

public class CustomSuggestionAdapter extends SuggestionsAdapter<Suggestion, SuggestionViewHolder> {

    private static final int TYPE_CITY = 0;
    private static final int TYPE_CHURCH = 1;
    private static final int TYPE_ADVANCED_SEARCH = 2;
    private static final int TYPE_RECENT_SEARCH = 3;

    private CustomSearchBar.SearchBarCallback searchBarCallback;

    public CustomSuggestionAdapter(LayoutInflater inflater, CustomSearchBar.SearchBarCallback searchBarCallback) {
        super(inflater);
        this.searchBarCallback = searchBarCallback;
    }

    @Override
    public void onBindSuggestionHolder(Suggestion suggestion, SuggestionViewHolder holder, int position) {
        holder.bind(suggestion.getData());
        holder.getRoot().setOnClickListener(view -> searchBarCallback.onSuggestionSelected(suggestion));
    }

    @Override
    public int getSingleViewHeight() {
        return 56;
    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CITY) {
            return new CitySuggestionViewHolder(getLayoutInflater().inflate(R.layout.item_suggestion_city, parent, false));
        } else if (viewType == TYPE_CHURCH) {
            return new ChurchSuggestionViewHolder(getLayoutInflater().inflate(R.layout.item_suggestion_church, parent, false));
        } else if (viewType == TYPE_ADVANCED_SEARCH) {
            return new AdvancedSearchSuggestionViewHolder(getLayoutInflater().inflate(R.layout.item_suggestion_advanced_search, parent, false));
        } else if (viewType == TYPE_RECENT_SEARCH) {
            return new RecentSearchSuggestionViewHolder(getLayoutInflater().inflate(R.layout.item_suggestion_recent_search, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (suggestions.get(position) instanceof CitySuggestion) {
            return TYPE_CITY;
        } else if (suggestions.get(position) instanceof ChurchSuggestion) {
            return TYPE_CHURCH;
        } else if (suggestions.get(position) instanceof AdvancedSearchSuggestion) {
            return TYPE_ADVANCED_SEARCH;
        } else {
            return TYPE_RECENT_SEARCH;
        }
    }
}
