package com.frama.miserend.hu.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.location.Location;

import com.frama.miserend.hu.database.miserend.dao.ChurchDao;
import com.frama.miserend.hu.database.miserend.dao.ChurchWithMassesDao;
import com.frama.miserend.hu.database.miserend.dao.MassesDao;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.home.pages.masses.model.MassComparator;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.utils.Validation;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MiserendRepository {

    private ChurchDao churchDao;
    private ChurchWithMassesDao churchWithMassesDao;
    private MassesDao massesDao;

    public MiserendRepository(ChurchDao churchDao, ChurchWithMassesDao churchWithMassesDao, MassesDao massesDao) {
        this.churchDao = churchDao;
        this.churchWithMassesDao = churchWithMassesDao;
        this.massesDao = massesDao;
    }

    public LiveData<List<Church>> getAllChurch() {
        return churchDao.getAll();
    }

    public LiveData<ChurchWithMasses> getChurch(int id) {
        return churchWithMassesDao.getChurchById(id);
    }

    public LiveData<List<ChurchWithMasses>> getChurches(List<Integer> ids) {
        return churchWithMassesDao.getChurchesById(ids);
    }

    public LiveData<PagedList<ChurchWithMasses>> getNearChurches(double latitude, double longitude) {
        return new LivePagedListBuilder<>(churchWithMassesDao.getNearChurches(latitude, longitude), 20).build();
    }

    public LiveData<List<Church>> getChurches(String name) {
        return churchDao.getByName(name);
    }

    public LiveData<List<String>> getCities() {
        return churchDao.getAllCities();
    }

    public LiveData<List<String>> getCities(String name) {
        return churchDao.getCities(name);
    }

    public LiveData<List<ChurchWithMasses>> getChurches(SearchParams searchParams) {
        if (Validation.notEmpty(searchParams.getSearchTerm())) {
            return churchWithMassesDao.getByName(searchParams.getSearchTerm());
        } else {
            return churchWithMassesDao.getBySearch(searchParams.getChurchName(), searchParams.getCity());
        }
    }

    public LiveData<List<MassWithChurch>> getRecommendedMasses(Location currentLocation) {
        LocalDate today = LocalDate.now();
        int dayOfWeek = today.getDayOfWeek().getValue();
        return Transformations.map(massesDao.getMassesInRadius(currentLocation.getLatitude(), currentLocation.getLongitude(), dayOfWeek),
                massWithChurches -> {
                    List<MassWithChurch> masses = new ArrayList<>();
                    for (MassWithChurch massWithChurch : massWithChurches) {
                        if (MassFilter.isMassOnDay(massWithChurch.getMass(), LocalDate.now())) {
                            masses.add(massWithChurch);
                        }
                    }
                    Collections.sort(masses, new MassComparator(currentLocation));
                    return masses;
                });
    }

    public LiveData<List<MassWithChurch>> getMasses(SearchParams searchParams) {
        return Transformations.map(massesDao.getMassesBySearch(searchParams.getChurchName(), searchParams.getCity(), searchParams.getDate().getDayOfWeek().getValue()),
                masses -> {
                    masses = MassFilter.filterMassWithChurchForDay(masses, searchParams.getDate());
                    if (!searchParams.isAllDay()) {
                        masses = MassFilter.filterMassWithChurchForTime(masses, searchParams.getFromTime(), searchParams.getToTime());
                    }
                    return masses;
                });
    }
}
