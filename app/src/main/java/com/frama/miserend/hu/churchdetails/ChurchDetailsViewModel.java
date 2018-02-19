package com.frama.miserend.hu.churchdetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
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

public class ChurchDetailsViewModel extends AndroidViewModel {

    private final LocalDatabase localDatabase;
    private final MiserendDatabase miserendDatabase;
    private final int churchId;

    private LiveData<Boolean> isFavorite;

    public ChurchDetailsViewModel(@NonNull Application application, int churchId, LocalDatabase localDatabase, MiserendDatabase miserendDatabase) {
        super(application);
        this.churchId = churchId;
        this.miserendDatabase = miserendDatabase;
        this.localDatabase = localDatabase;
    }

    public LiveData<ChurchWithMasses> getChurchWithMasses() {
        return miserendDatabase.churchWithMassesDao().getChurchById(churchId);
    }

    public LiveData<Boolean> isFavorite(int churchId) {
        if (isFavorite == null) {
            isFavorite = Transformations.map(localDatabase.favoritesDao().getCountById(churchId), count -> count > 0);
        }
        return isFavorite;
    }

    public void toggleFavorite() {
        if (isFavorite.getValue()) {
            removeFavorite();
        } else {
            addFavorite();
        }
    }

    public void addFavorite() {
        Observable.just(localDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe(db -> db.favoritesDao().insert(new Favorite(churchId)));
    }

    public void removeFavorite() {
        Observable.just(localDatabase)
                .subscribeOn(Schedulers.io())
                .subscribe(db -> db.favoritesDao().delete(new Favorite(churchId)));
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final LocalDatabase localDatabase;
        private final MiserendDatabase miserendDatabase;
        private final int churchId;


        public Factory(@NonNull Application application, int churchId, LocalDatabase localDatabase, MiserendDatabase miserendDatabase) {
            this.application = application;
            this.localDatabase = localDatabase;
            this.miserendDatabase = miserendDatabase;
            this.churchId = churchId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChurchDetailsViewModel(application, churchId, localDatabase, miserendDatabase);
        }
    }
}
