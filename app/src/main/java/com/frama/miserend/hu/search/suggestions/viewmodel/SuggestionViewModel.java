package com.frama.miserend.hu.search.suggestions.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.livedata.ListMergerLiveData;
import com.frama.miserend.hu.repository.MiserendRepository;
import com.frama.miserend.hu.repository.RecentSearchesRepository;
import com.frama.miserend.hu.search.suggestions.Suggestion;
import com.frama.miserend.hu.search.suggestions.advanced.AdvancedSearchSuggestion;
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestion;
import com.frama.miserend.hu.search.suggestions.city.CitySuggestion;
import com.frama.miserend.hu.search.suggestions.recent.RecentSearchSuggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 15..
 */

public class SuggestionViewModel extends AndroidViewModel {

    private MiserendRepository miserendRepository;
    private RecentSearchesRepository recentSearchesRepository;

    private MutableLiveData<String> lastSearchTerm;
    private MutableLiveData<List<Suggestion>> suggestions;

    public SuggestionViewModel(@NonNull Application application, MiserendRepository miserendRepository, RecentSearchesRepository recentSearchesRepository) {
        super(application);
        this.miserendRepository = miserendRepository;
        this.recentSearchesRepository = recentSearchesRepository;
        lastSearchTerm = new MutableLiveData<>();
        suggestions = new MutableLiveData<>();
    }

    public LiveData<List<Suggestion>> getSuggestions() {
        return Transformations.switchMap(lastSearchTerm, searchTerm -> {
            if (searchTerm.length() > 2) {
                return Transformations.map(new ListMergerLiveData<>(
                                getChurchSuggestions(searchTerm),
                                getCitySuggestions(searchTerm),
                                getRecentSearchSuggestions(searchTerm)),
                        results -> {
                            results.add(new AdvancedSearchSuggestion());
                            return results;
                        });
            } else {
                return Transformations.map(getRecentSearchSuggestions(searchTerm),
                        results -> {
                            results.add(new AdvancedSearchSuggestion());
                            return results;
                        });
            }
        });
    }

    public void updateSuggestions(String searchTerm) {
        lastSearchTerm.setValue(searchTerm);
    }

    private LiveData<List<Suggestion>> getChurchSuggestions(String searchTerm) {
        return Transformations.map(miserendRepository.getChurches(searchTerm),
                churches -> {
                    List<Suggestion> names = new ArrayList<>();
                    for (Church church : churches) {
                        names.add(new ChurchSuggestion(church));
                    }
                    return names;
                });
    }

    private LiveData<List<Suggestion>> getCitySuggestions(String searchTerm) {
        return Transformations.map(miserendRepository.getCities(searchTerm),
                cities -> {
                    List<Suggestion> names = new ArrayList<>();
                    for (String city : cities) {
                        names.add(new CitySuggestion(city));
                    }
                    return names;
                });
    }

    private LiveData<List<Suggestion>> getRecentSearchSuggestions(String searchTerm) {
        return Transformations.map(recentSearchesRepository.getRecentSearches(searchTerm),
                recents -> {
                    List<Suggestion> searchTerms = new ArrayList<>();
                    for (String recent : recents) {
                        searchTerms.add(new RecentSearchSuggestion(recent));
                    }
                    return searchTerms;
                });
    }

    public void addRecentSearch(String searchTerm) {
        new Thread(() -> recentSearchesRepository.add(searchTerm));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final MiserendRepository miserendRepository;
        private final RecentSearchesRepository recentSearchesRepository;

        public Factory(@NonNull Application mApplication, MiserendRepository miserendRepository, RecentSearchesRepository recentSearchesRepository) {
            this.mApplication = mApplication;
            this.miserendRepository = miserendRepository;
            this.recentSearchesRepository = recentSearchesRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SuggestionViewModel(mApplication, miserendRepository, recentSearchesRepository);
        }
    }
}
