package com.frama.miserend.hu.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.frama.miserend.hu.R;

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
}
