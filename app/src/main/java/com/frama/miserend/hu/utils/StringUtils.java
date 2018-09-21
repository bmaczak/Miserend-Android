package com.frama.miserend.hu.utils;

import java.util.Scanner;

/**
 * Created by Balazs_Maczak on 1/20/2016.
 */
public class StringUtils {

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        Scanner sc = new Scanner(s.trim());
        if (!sc.hasNextInt(radix))
            return false;
        // we know it starts with a valid int, now make sure
        // there's nothing left!
        sc.nextInt(radix);
        return !sc.hasNext();
    }

    public static String capitalizeFirstLetter(String string) {
        return string.length() > 0 ? string.substring(0, 1).toUpperCase() + string.substring(1) : string;
    }

    public static String getDistanceText(float distance) {
        if (distance < 1000) {
            return ((int) distance) + " m";
        } else {
            return String.format("%.2f km", (distance / 1000));
        }
    }
}
