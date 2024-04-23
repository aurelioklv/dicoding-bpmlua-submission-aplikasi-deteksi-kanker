package com.dicoding.asclepius.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun parseTimestamp(timestamp: Long): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Date(timestamp)
    return simpleDateFormat.format(date)
}