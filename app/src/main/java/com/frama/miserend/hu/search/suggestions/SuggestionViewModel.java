package com.frama.miserend.hu.search.suggestions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.search.suggestions.advanced.AdvancedSearchSuggestion;
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestion;
import com.frama.miserend.hu.search.suggestions.city.CitySuggestion;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 15..
 */

public class SuggestionViewModel extends AndroidViewModel {

    private MiserendDatabase miserendDatabase;

    private MutableLiveData<List<Suggestion>> suggestions;

    public SuggestionViewModel(@NonNull Application application, MiserendDatabase miserendDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
        suggestions = new MutableLiveData<>();
    }

    public MutableLiveData<List<Suggestion>> getSuggestions() {
        return suggestions;
    }

    public void updateSuggestions(String searchTerm) {
        if (searchTerm.length() > 2) {
            Flowable.zip(getChurchSuggestions(searchTerm), getCitySuggestions(searchTerm),
                    (suggestions, suggestions2) -> {
                        suggestions.addAll(suggestions2);
                        suggestions.add(0, new AdvancedSearchSuggestion());
                        return suggestions;
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(suggestionResult -> suggestions.setValue(suggestionResult));
        } else {
            List<Suggestion> suggestionList = new ArrayList<>();
            suggestionList.add(new AdvancedSearchSuggestion());
            suggestions.setValue(suggestionList);
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

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final MiserendDatabase miserendDatabase;

        public Factory(@NonNull Application mApplication, MiserendDatabase miserendDatabase) {
            this.mApplication = mApplication;
            this.miserendDatabase = miserendDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SuggestionViewModel(mApplication, miserendDatabase);
        }
    }
}
