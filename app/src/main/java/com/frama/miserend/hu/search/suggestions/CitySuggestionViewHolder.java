package com.frama.miserend.hu.search.suggestions;

import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maczak on 2018. 02. 15..
 */

public class CitySuggestionViewHolder extends SuggestionViewHolder<String> {

    @BindView(R.id.suggestion_city_name)
    TextView cityName;

    public CitySuggestionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(String data) {
        cityName.setText(data);
    }
}
