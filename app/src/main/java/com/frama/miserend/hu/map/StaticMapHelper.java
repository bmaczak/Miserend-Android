package com.frama.miserend.hu.map;

import android.content.Context;

import com.frama.miserend.hu.R;

import java.util.Locale;

/**
 * Created by maczak on 2018. 02. 21..
 */

public class StaticMapHelper {
    public static String getSaticMapUrl(Context context, double latitude, double longitude, int width, int height) {
        return String.format(Locale.US, context.getString(R.string.static_map_url), latitude, longitude, width / 2, height / 2);
    }
}
