package com.dicoding.asclepius.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun parseTimestamp(timestamp: Long): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Date(timestamp)
    return simpleDateFormat.format(date)
}

fun convertTimestampToString(
    timestampString: String,
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
    outputFormat: String = "yyyy-MM-dd HH:mm:ss",
): String {
    val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    inputDateFormat.timeZone = TimeZone.getTimeZone("UTC") // Set input timezone if known

    return try {
        val parsedDate = inputDateFormat.parse(timestampString)
        outputDateFormat.format(parsedDate)
    } catch (e: Exception) {
        // Handle parse errors
        e.printStackTrace()
        ""
    }
}