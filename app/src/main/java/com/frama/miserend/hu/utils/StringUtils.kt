package com.frama.miserend.hu.utils

import java.util.Scanner

/**
 * Created by Balazs_Maczak on 1/20/2016.
 */
object StringUtils {

    @JvmOverloads
    fun isInteger(s: String, radix: Int = 10): Boolean {
        val sc = Scanner(s.trim { it <= ' ' })
        if (!sc.hasNextInt(radix))
            return false
        // we know it starts with a valid int, now make sure
        // there's nothing left!
        sc.nextInt(radix)
        return !sc.hasNext()
    }

    fun capitalizeFirstLetter(string: String): String {
        return if (string.isNotEmpty()) string.substring(0, 1).toUpperCase() + string.substring(1) else string
    }
}
