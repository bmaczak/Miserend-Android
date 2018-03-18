package com.frama.miserend.hu.search.suggestions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.local.entities.RecentSearch;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.search.suggestions.advanced.AdvancedSearchSuggestion;
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestion;
import com.frama.miserend.hu.search.suggestions.city.CitySuggestion;
import com.frama.miserend.hu.search.suggestions.recent.RecentSearchSuggestion;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 15..
 */

public class SuggestionViewModel extends AndroidViewModel {

    private MiserendDatabase miserendDatabase;
    private LocalDatabase localDatabase;

    private MutableLiveData<List<Suggestion>> suggestions;

    public SuggestionViewModel(@NonNull Application application, MiserendDatabase miserendDatabase, LocalDatabase localDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
        this.localDatabase = localDatabase;
        suggestions = new MutableLiveData<>();
    }

    public MutableLiveData<List<Suggestion>> getSuggestions() {
        return suggestions;
    }

    public void updateSuggestions(String searchTerm) {
        if (searchTerm.length() > 2) {
            Flowable.zip(getRecentSearchSuggestions(searchTerm), getChurchSuggestions(searchTerm), getCitySuggestions(searchTerm),
                    (suggestions, suggestions2, suggestions3) -> {
                        suggestions.addAll(suggestions2);
                        suggestions.addAll(suggestions3);
                        suggestions.add(0, new AdvancedSearchSuggestion());
                        return suggestions;
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(suggestionResult -> suggestions.setValue(suggestionResult));
        } else {
            getRecentSearchSuggestions(searchTerm)
                    .map(suggestions -> {
                        suggestions.add(0, new AdvancedSearchSuggestion());
                        return suggestions;
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(suggestionResult -> suggestions.setValue(suggestionResult));
        }

    }

    private Flowable<List<Suggestion>> getChurchSuggestions(String searchTerm) {
        return miserendDatabase.churchDao().getByName(searchTerm)
                .map(churches -> {
                    List<Suggestion> names = new ArrayList<>();
                    for (Church church : churches) {
                        names.add(new ChurchSuggestion(church));
                    }
                    return names;
                });
    }

    private Flowable<List<Suggestion>> getCitySuggestions(String searchTerm) {
        return miserendDatabase.churchDao().getCities(searchTerm)
                .map(cities -> {
                    List<Suggestion> names = new ArrayList<>();
                    for (String city : cities) {
                        names.add(new CitySuggestion(city));
                    }
                    return names;
                });
    }

    private Flowable<List<Suggestion>> getRecentSearchSuggestions(String searchTerm) {
        return localDatabase.recentSearchesDao().getBySearchTerm(searchTerm)
                .map(recents -> {
                    List<Suggestion> searchTerms = new ArrayList<>();
                    for (String recent : recents) {
                        searchTerms.add(new RecentSearchSuggestion(recent));
                    }
                    return searchTerms;
                });
    }

    public void addRecentSearch(String searchTerm) {
        Observable.just(localDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe(db -> db.recentSearchesDao().insert(new RecentSearch(searchTerm)));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final MiserendDatabase miserendDatabase;
        private final LocalDatabase localDatabase;

        public Factory(@NonNull Application mApplication, MiserendDatabase miserendDatabase, LocalDatabase localDatabase) {
            this.mApplication = mApplication;
            this.miserendDatabase = miserendDatabase;
            this.localDatabase = localDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SuggestionViewModel(mApplication, miserendDatabase, localDatabase);
        }
    }
}
