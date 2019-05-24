package com.frama.miserend.hu.utils

import android.content.res.Resources

import com.frama.miserend.hu.R

import org.threeten.bp.LocalDate

import org.threeten.bp.temporal.ChronoUnit.DAYS

object DateUtils {

    fun LocalDate.getNameOfDay(resources: Resources): String {
        val daysBetween = LocalDate.now().daysBetween(this)
        return when (daysBetween) {
            0L -> resources.getString(R.string.today)
            1L -> resources.getString(R.string.tomorrow)
            else -> {
                val massDay = this.dayOfWeek.value
                resources.getStringArray(
                        R.array.days_of_week)[massDay - 1]
            }
        }
    }

    private fun LocalDate.daysBetween(endDate: LocalDate): Long {
        return DAYS.between(this, endDate)
    }

    fun cutSecondsFromTime(time: String?): String? {
        return if (null != time && time.isNotEmpty()) {
            val endIndex = time.lastIndexOf(":")
            if (endIndex != -1) {
                time.substring(0, endIndex)
            } else {
                time
            }
        } else {
            time
        }
    }

}
