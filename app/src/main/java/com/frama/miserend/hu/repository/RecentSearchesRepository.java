package com.frama.miserend.hu.repository;

import android.arch.lifecycle.LiveData;

import com.frama.miserend.hu.database.local.dao.RecentSearchesDao;
import com.frama.miserend.hu.database.local.entities.RecentSearch;

import java.util.Calendar;
import java.util.List;

public class RecentSearchesRepository {

    private RecentSearchesDao recentSearchesDao;

    public RecentSearchesRepository(RecentSearchesDao recentSearchesDao) {
        this.recentSearchesDao = recentSearchesDao;
    }

    public LiveData<List<String>> getRecentSearches() {
        return recentSearchesDao.getAll();
    }

    public LiveData<List<String>> getRecentSearches(String searchTerm) {
        return recentSearchesDao.getBySearchTerm(searchTerm);
    }

    public void add(String searchTerm) {
        recentSearchesDao.insert(new RecentSearch(searchTerm, Calendar.getInstance().getTimeInMillis()));
    }

}
