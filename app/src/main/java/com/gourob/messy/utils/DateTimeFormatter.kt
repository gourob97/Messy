package com.gourob.messy.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeFormat {
    const val DEFAULT = "dd-MM-yyyy HH:mm:ss"
    const val DD_MM_YYYY = "dd-MM-yyyy"
    const val DD_MM_YY_SLASHED = "dd/MM/yy"
    const val DD_MMMM_YYYY = "dd MMM, yyyy"
}


fun formatMillisToDate(millis: Long, format: String = DateTimeFormat.DEFAULT): String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    val date = Date(millis)
    return sdf.format(date)
}