package com.frama.miserend.hu.home.pages.churches.favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.local.entities.Favorite;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class FavoritesViewModel extends AndroidViewModel {

    private final LocalDatabase localDatabase;
    private final MiserendDatabase miserendDatabase;

    private LiveData<List<Integer>> favorites;

    public FavoritesViewModel(@NonNull Application application, LocalDatabase localDatabase, MiserendDatabase miserendDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
        this.localDatabase = localDatabase;
    }

    public LiveData<List<Integer>> getFavorites() {
        if (favorites == null) {
            favorites = localDatabase.favoritesDao().getAll();
        }
        return favorites;
    }

    public LiveData<List<ChurchWithMasses>> getFavoriteChurches() {
        return miserendDatabase.churchWithMassesDao().getChurchesById(favorites.getValue());
    }

    public void toggleFavorite(int churchId) {
        if (favorites.getValue() != null) {
            if (favorites.getValue().contains(churchId)) {
                removeFavorite(churchId);
            } else {
                addFavorite(churchId);
            }
        }
    }

    public void addFavorite(int churchId) {
        Observable.just(localDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe(db -> db.favoritesDao().insert(new Favorite(churchId)));
    }

    public void removeFavorite(int churchId) {
        Observable.just(localDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe(db -> db.favoritesDao().delete(new Favorite(churchId)));
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final LocalDatabase localDatabase;
        private final MiserendDatabase miserendDatabase;


        public Factory(@NonNull Application application, LocalDatabase localDatabase, MiserendDatabase miserendDatabase) {
            this.application = application;
            this.localDatabase = localDatabase;
            this.miserendDatabase = miserendDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavoritesViewModel(application, localDatabase, miserendDatabase);
        }
    }
}
