package com.frama.miserend.hu.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public class ListMergerLiveData<T> extends MediatorLiveData<List<T>> {

    public SparseArray<List<T>> lists;

    public ListMergerLiveData(LiveData<List<T>>... sources) {
        lists = new SparseArray<>();
        for (int i = 0; i < sources.length; ++i) {
            int finalI = i;
            addSource(sources[i], t -> {
                lists.put(finalI, t);
                update();
            });
        }
    }

    public void update() {
        List<T> value = new ArrayList<>();
        for (int i = 0; i < lists.size(); ++i) {
            if (lists.get(i) != null) {
                value.addAll(lists.get(i));
            }
        }
        setValue(value);
    }
}
