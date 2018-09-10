package com.frama.miserend.hu.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.frama.miserend.hu.database.local.dao.FavoritesDao;
import com.frama.miserend.hu.database.local.entities.Favorite;

import java.util.List;

public class FavoritesRepository {

    private FavoritesDao favoritesDao;

    public FavoritesRepository(FavoritesDao favoritesDao) {
        this.favoritesDao = favoritesDao;
    }

    public LiveData<List<Integer>> getFavorites() {
        return favoritesDao.getAll();
    }

    public void toggleFavorite(int churchId) {
        new Thread(() -> {
            if (favoritesDao.getCountById(churchId) == 1) {
                removeFavorite(churchId);
            } else {
                addFavorite(churchId);
            }
        }).start();
    }

    public LiveData<Boolean> isFavorite(int churchId) {
        return Transformations.map(getFavorites(), favorites -> favorites.contains(churchId));
    }

    public void addFavorite(int churchId) {
        favoritesDao.insert(new Favorite(churchId));
    }

    public void removeFavorite(int churchId) {
        favoritesDao.delete(new Favorite(churchId));
    }
}
