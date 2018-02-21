package com.frama.miserend.hu.churchdetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Mass;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 21..
 */

public class MassListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date)
    TextView dateText;
    @BindView(R.id.mass_list)
    TextView massList;

    public MassListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(DayOfMasses dayOfMasses) {
        dateText.setText(dayOfMasses.getDay().get(Calendar.DAY_OF_MONTH) + ".");
        StringBuilder massesStringBuilder = new StringBuilder();
        for (Mass mass : dayOfMasses.getMasses()) {
            massesStringBuilder.append(mass.getTime()).append("\n");
        }
        massList.setText(massesStringBuilder.toString());
    }
}
