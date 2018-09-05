package com.frama.miserend.hu.churchdetails.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.repository.MiserendRepository;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class ChurchDetailsViewModel extends AndroidViewModel {

    private final MiserendRepository miserendRepository;
    private final int churchId;

    public ChurchDetailsViewModel(@NonNull Application application, int churchId, MiserendRepository miserendRepository) {
        super(application);
        this.churchId = churchId;
        this.miserendRepository = miserendRepository;
    }

    public LiveData<ChurchWithMasses> getChurchWithMasses() {
        return miserendRepository.getChurch(churchId);
    }

    public int getChurchId() {
        return churchId;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final MiserendRepository miserendRepository;
        private final int churchId;


        public Factory(@NonNull Application application, int churchId, MiserendRepository miserendRepository) {
            this.application = application;
            this.miserendRepository = miserendRepository;
            this.churchId = churchId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChurchDetailsViewModel(application, churchId, miserendRepository);
        }
    }
}
