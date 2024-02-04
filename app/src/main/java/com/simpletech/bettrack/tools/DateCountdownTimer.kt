package com.simpletech.bettrack.tools

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun Date.getCountDownFlow(): Flow<String> = flow {
    val sdf = SimpleDateFormat("dd'd' HH:mm:ss", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    val targetCalendar = Calendar.getInstance()
    targetCalendar.timeZone = TimeZone.getTimeZone("UTC")
    targetCalendar.time = this@getCountDownFlow
    val targetMillis = targetCalendar.timeInMillis
    val timeDifference = targetMillis.minus(System.currentTimeMillis())
    if (timeDifference <= 0) emit("Now")
    else {
        for (millisUntilFinished in timeDifference downTo 0 step 1000) {
            val countdownText = sdf.format(Date(millisUntilFinished))
            emit(countdownText)
            delay(1000)
        }
    }
    emit("Now")
}
