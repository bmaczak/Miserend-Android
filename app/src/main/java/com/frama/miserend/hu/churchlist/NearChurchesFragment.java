package com.frama.miserend.hu.churchlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.di.components.ChurchListComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class NearChurchesFragment extends Fragment {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    NearChurchesViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ChurchListComponent.Injector.inject(this);
        View v = inflater.inflate(R.layout.fragment_near_churches, container, false);
        ButterKnife.bind(this, v);
        NearChurchesAdapter adapter = new NearChurchesAdapter();
        viewModel.getChurches().observe(this, adapter::setList);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
