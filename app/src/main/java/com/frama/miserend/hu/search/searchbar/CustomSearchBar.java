package com.frama.miserend.hu.search.searchbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;

import com.frama.miserend.hu.search.suggestions.Suggestion;
import com.frama.miserend.hu.search.suggestions.view.CustomSuggestionAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;

/**
 * Created by Balazs on 2018. 02. 16..
 */

public class CustomSearchBar extends MaterialSearchBar {

    private SearchBarCallback searchBarCallback;

    public CustomSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public CustomSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public CustomSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    public void setSearchBarCallback(SearchBarCallback searchBarCallback) {
        this.searchBarCallback = searchBarCallback;
        setup();
    }

    public void setup() {
        addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    searchBarCallback.onSearchTermChanged(charSequence.toString());
                } else {
                    clearSuggestions();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                searchBarCallback.onSearchStateChanged(enabled);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                searchBarCallback.onSearchConfirmed(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

        setCustomSuggestionAdapter(new CustomSuggestionAdapter(LayoutInflater.from(getContext()), searchBarCallback));

        RecyclerView suggestionsRecyclerView = findViewById(com.mancj.materialsearchbar.R.id.mt_recycler);
        suggestionsRecyclerView.setNestedScrollingEnabled(false);
        suggestionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
                }
            }
        });

    }

    public void close() {
        clearFocus();
        hideSuggestionsList();
        disableSearch();
    }

    public interface SearchBarCallback {
        void onSearchTermChanged(String searchTerm);

        void onSearchStateChanged(boolean enabled);

        void onSuggestionSelected(Suggestion suggestion);

        void onSearchConfirmed(String searchTerm);
    }
}
