package com.frama.miserend.hu.utils

/**
 * Created by Balazs on 2018. 03. 19..
 */

object Validation {
    fun isEmpty(text: String): Boolean {
        return !notEmpty(text)
    }

    fun notEmpty(text: String?): Boolean {
        return text != null && !text.isEmpty()
    }
}
