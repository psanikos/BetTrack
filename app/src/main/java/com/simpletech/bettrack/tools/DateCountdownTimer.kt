package com.simpletech.bettrack.tools

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


fun Date.getCountDownFlow(): Flow<String> = flow {
    val timeDifferenceMillis = this@getCountDownFlow.time - System.currentTimeMillis()
    if (timeDifferenceMillis <= 0) {
        emit("Now")
    } else {
        for (millisUntilFinished in timeDifferenceMillis downTo 0 step 1000) {
            val countdownText = formatMilliseconds(millisUntilFinished)
            emit(countdownText)
            delay(1000)
        }
    }
    emit("Now")
}

private fun formatMilliseconds(millis: Long): String {
    val days = TimeUnit.MILLISECONDS.toDays(millis)
    val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

    return if (days > 0) {
        String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes, seconds)
    } else {
        String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    }
}