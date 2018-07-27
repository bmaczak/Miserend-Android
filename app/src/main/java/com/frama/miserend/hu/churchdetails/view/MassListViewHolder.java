package com.frama.miserend.hu.churchdetails.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.utils.DateUtils;
import com.frama.miserend.hu.utils.ViewUtils;
import com.google.android.flexbox.FlexboxLayout;

import org.threeten.bp.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 21..
 */

public class MassListViewHolder extends RecyclerView.ViewHolder {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd.");

    @BindView(R.id.date)
    TextView dateText;
    @BindView(R.id.name_of_day)
    TextView dayText;
    @BindView(R.id.mass_flexbox)
    FlexboxLayout flexboxLayout;

    private LayoutInflater layoutInflater;

    public MassListViewHolder(View itemView) {
        super(itemView);
        this.layoutInflater = LayoutInflater.from(itemView.getContext());
        ButterKnife.bind(this, itemView);
    }

    public void bind(DayOfMasses dayOfMasses, OnMassClickedListener onMassClickedListener) {
        dateText.setText(dayOfMasses.getDay().format(dateFormatter));
        dayText.setText(DateUtils.getNameOfDay(dayText.getResources(), dayOfMasses.getDay()));

        flexboxLayout.removeAllViews();
        for (Mass mass : dayOfMasses.getMasses()) {
            View view = ViewUtils.createMassFlexboxItem(layoutInflater, flexboxLayout, mass);
            view.setOnClickListener(view1 -> onMassClickedListener.onMassClicked(mass));
            flexboxLayout.addView(view);
        }
    }
}
