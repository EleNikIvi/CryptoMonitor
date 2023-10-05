package com.example.cryptomonitor.core

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtils {

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_TIME_API_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"

    fun getCurrentDateTimeFormatted(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        return currentDateTime.format(formatter)
    }

    fun formatToLocalDateTime(dateTime: String): String {
        if (dateTime.isEmpty()) return ""
        val formatterUtc = DateTimeFormatter.ofPattern(DATE_TIME_API_FORMAT, Locale.getDefault())
        val localDate = LocalDateTime.parse(dateTime, formatterUtc)
            .atOffset(ZoneOffset.UTC)
            .atZoneSameInstant(ZoneId.systemDefault())
        val formatterDefault = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        return localDate.format(formatterDefault)
    }
}