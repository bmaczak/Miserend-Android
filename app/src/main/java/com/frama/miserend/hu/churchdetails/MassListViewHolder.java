package com.frama.miserend.hu.churchdetails;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    @BindView(R.id.mass_list)
    TextView massList;

    private SimpleDateFormat simpleDateFormat;

    public MassListViewHolder(View itemView) {
        super(itemView);
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ROOT);
        ButterKnife.bind(this, itemView);
    }

    public void bind(DayOfMasses dayOfMasses) {
        dateText.setText(simpleDateFormat.format(dayOfMasses.getDay().getTime()));
        dayText.setText(DateUtils.getNameOfDay(dayText.getResources(), dayOfMasses.getDay()));
        List<String> massStrings = new ArrayList<>();
        for (Mass mass : dayOfMasses.getMasses()) {
            massStrings.add(DateUtils.cutSecondsFromTime(mass.getTime()));
        }
        massList.setText(TextUtils.join(", ", massStrings));
    }
}
