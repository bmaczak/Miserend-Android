package com.frama.miserend.hu.churchdetails;

import android.content.Context;

import com.frama.miserend.hu.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maczak on 2018. 03. 20..
 */

public enum MassTag {
    G(R.string.tag_g),
    CS(R.string.tag_cs),
    CSAL(R.string.tag_csal),
    D(R.string.tag_d),
    IFI(R.string.tag_ifi),
    IGE(R.string.tag_ige),
    SZENT(R.string.tag_szent),
    UTR(R.string.tag_utr),
    VECS(R.string.tag_vecs),
    GOR(R.string.tag_gor),
    ROM(R.string.tag_rom),
    REGI(R.string.tag_regi);

    private static Map<String, MassTag> tagMap;

    static {
        tagMap = new HashMap<>();
        for (MassTag tag : MassTag.values()) {
            tagMap.put(tag.name().toLowerCase(), tag);
        }
    }

    public static String getTagDescription(Context context, String string) {
        if (Character.isDigit(string.charAt(string.length() - 1))) {
            String withoutNumber = string.substring(0, string.length() - 1);
            String number = string.substring(string.length() - 1);
            return context.getString(tagMap.get(withoutNumber).getLabel()) + " minden " + number + ". héten.";
        } else {
            return context.getString(tagMap.get(string).getLabel());
        }
    }

    MassTag(int label) {
        this.label = label;
    }

    private int label;

    public int getLabel() {
        return label;
    }
}
