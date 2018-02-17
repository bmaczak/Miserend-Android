package com.frama.miserend.hu.home.pages.churches.favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.local.entities.Favorite;
import com.frama.miserend.hu.database.miserend.manager.DatabaseDownloaderTask;
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager;
import com.frama.miserend.hu.database.miserend.manager.DatabaseState;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class FavoritesViewModel extends AndroidViewModel {

    private final LocalDatabase localDatabase;

    private LiveData<List<Integer>> favorites;

    public FavoritesViewModel(@NonNull Application application, LocalDatabase localDatabase) {
        super(application);
        this.localDatabase = localDatabase;
    }

    public LiveData<List<Integer>> getFavorites() {
        if (favorites == null) {
            favorites = localDatabase.favoritesDao().getAll();
        }
        return favorites;
    }

    public void toggleFavorite(int churchId) {
        if (favorites.getValue().contains(churchId)) {
            removeFavorite(churchId);
        } else {
            addFavorite(churchId);
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
        private final Application mApplication;

        private final LocalDatabase localDatabase;

        public Factory(@NonNull Application application, LocalDatabase localDatabase) {
            this.mApplication = application;
            this.localDatabase = localDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavoritesViewModel(mApplication, localDatabase);
        }
    }
}
