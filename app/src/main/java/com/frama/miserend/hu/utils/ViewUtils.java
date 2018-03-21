package com.frama.miserend.hu.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by Balazs on 2018. 02. 14..
 */

public class ViewUtils {
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static void setTextOrHide(TextView textView, String text) {
        if (text != null && text.length() > 0) {
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public static View createMassFlexboxItem(LayoutInflater layoutInflater, FlexboxLayout parent, Mass mass) {
        View view = layoutInflater.inflate(R.layout.item_mass_flexbox_element, parent, false);
        ((TextView) view.findViewById(R.id.mass_time)).setText(DateUtils.cutSecondsFromTime(mass.getTime()));
        view.findViewById(R.id.mass_info_icon).setVisibility(mass.hasInfo() ? View.VISIBLE : View.GONE);
        return view;
    }
}
