package com.frama.miserend.hu.utils;

/**
 * Created by Balazs on 2018. 03. 19..
 */

public class Validation {
    public static boolean isEmpty(String text) {
        return !notEmpty(text);
    }

    public static boolean notEmpty(String text) {
        return text != null && !text.isEmpty();
    }
}
