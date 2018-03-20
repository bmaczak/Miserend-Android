package com.frama.miserend.hu.churchdetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.utils.DateUtils;
import com.frama.miserend.hu.utils.Validation;
import com.google.android.flexbox.FlexboxLayout;

import java.text.SimpleDateFormat;
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

    public void bind(DayOfMasses dayOfMasses, OnMassClickedListener onMassClickedListener) {
        dateText.setText(simpleDateFormat.format(dayOfMasses.getDay().getTime()));
        dayText.setText(DateUtils.getNameOfDay(dayText.getResources(), dayOfMasses.getDay()));

        flexboxLayout.removeAllViews();
        for (Mass mass : dayOfMasses.getMasses()) {
            View view = createFlexboxItem(mass);
            view.setOnClickListener(view1 -> onMassClickedListener.onMassClicked(mass));
            flexboxLayout.addView(view);
        }
    }

    private View createFlexboxItem(Mass mass) {
        View view = layoutInflater.inflate(R.layout.item_mass_flexbox_element, flexboxLayout, false);
        ((TextView) view.findViewById(R.id.mass_time)).setText(DateUtils.cutSecondsFromTime(mass.getTime()));
        view.findViewById(R.id.mass_info_icon).setVisibility(hasInfo(mass) ? View.VISIBLE : View.GONE);
        return view;
    }

    private boolean hasInfo(Mass mass) {
        return Validation.notEmpty(mass.getComment()) || Validation.notEmpty(mass.getTags()) || (mass.getPeriod() != null && !mass.getPeriod().equals("0"));
    }
}
