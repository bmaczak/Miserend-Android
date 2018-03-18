package com.frama.miserend.hu.churchdetails;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.utils.DateUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 21..
 */

public class MassListViewHolder extends RecyclerView.ViewHolder {

    private static String DATE_FORMAT = "yyyy. MM. dd.";

    @BindView(R.id.date)
    TextView dateText;
    @BindView(R.id.name_of_day)
    TextView dayText;
    @BindView(R.id.mass_flexbox)
    FlexboxLayout flexboxLayout;

    private LayoutInflater layoutInflater;
    private SimpleDateFormat simpleDateFormat;

    public MassListViewHolder(View itemView) {
        super(itemView);
        this.layoutInflater = LayoutInflater.from(itemView.getContext());
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ROOT);
        ButterKnife.bind(this, itemView);
    }

    public void bind(DayOfMasses dayOfMasses) {
        dateText.setText(simpleDateFormat.format(dayOfMasses.getDay().getTime()));
        dayText.setText(DateUtils.getNameOfDay(dayText.getResources(), dayOfMasses.getDay()));

        flexboxLayout.removeAllViews();
        for (Mass mass : dayOfMasses.getMasses()) {
            flexboxLayout.addView(createFlexboxItem(mass));
        }
    }

    private View createFlexboxItem(Mass mass) {
        View view = layoutInflater.inflate(R.layout.item_mass_flexbox_element, flexboxLayout, false);
        ((TextView) view.findViewById(R.id.mass_time)).setText(DateUtils.cutSecondsFromTime(mass.getTime()));
        return view;
    }
}
