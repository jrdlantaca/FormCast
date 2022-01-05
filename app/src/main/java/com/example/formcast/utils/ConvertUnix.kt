package com.example.formcast.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun convertUnix(timestamp: Long): String {
    val cal = Calendar.getInstance()
    val tz = cal.timeZone
    val outputFormat = SimpleDateFormat.getDateTimeInstance()

    outputFormat.timeZone = tz

    return outputFormat.format(Date(timestamp * 1000L))
}

fun convertUnixToHours(timestamp: Long): String {
    val cal = Calendar.getInstance()
    val date = Date(timestamp * 1000L)
    cal.time = date
    var hour = cal.get(Calendar.HOUR)
    if (hour == 0) {
        hour = 12
    }
    return if (cal.get(Calendar.AM_PM) == Calendar.AM) {
        "$hour AM"
    } else {
        "$hour PM"
    }
}

fun convertUnixToDay(index: Int, timestamp: Long): String {
    return if (index == 0) {
        "Today"
    } else {
        SimpleDateFormat("EEEE", Locale.getDefault()).format(timestamp * 1000L)
    }
}