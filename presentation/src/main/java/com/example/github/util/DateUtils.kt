package com.example.github.util

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val YYYY_MM_DD_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun convertDateToDay(date: String): String {
    val sdf = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_Z)
    sdf.timeZone = TimeZone.getTimeZone("GMT")
    return try {
        val time: Long = sdf.parse(date).time
        val now = System.currentTimeMillis()
        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString()
    } catch (e: ParseException) {
        ""
    }
}