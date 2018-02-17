package com.frama.miserend.hu.search.suggestions.church;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.search.suggestions.SuggestionViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 17..
 */

public class ChurchSuggestionViewHolder extends SuggestionViewHolder<Church> {

    @BindView(R.id.suggestion_church_name)
    TextView churchName;
    @BindView(R.id.suggestion_church_city)
    TextView churchCity;
    @BindView(R.id.church_thumb)
    SimpleDraweeView churchThumbnail;

    public ChurchSuggestionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Church data) {
        churchName.setText(data.getName());
        churchCity.setText(data.getCity());
        churchThumbnail.setImageURI(data.getImageUrl());
    }
}
