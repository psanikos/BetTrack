package com.simpletech.bettrack.tools

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Calendar
import java.util.Date


@RunWith(JUnit4::class)
class DateCountdownTimerTest {
    private lateinit var targetDate: Date
    private lateinit var job: Job
    private val scope = CoroutineScope(SupervisorJob())

    @Test
    fun testDistance() = runBlocking {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, 1)
        targetDate = calendar.time

        val distance = targetDate.getCountDownFlow().firstOrNull()
        assert(distance == "00:59:59")
    }

    @Test
    fun testEnd() = runBlocking {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 3)
        targetDate = calendar.time

        job = scope.launch {
            targetDate.getCountDownFlow().collectLatest {
                if (System.currentTimeMillis() >= calendar.timeInMillis) {
                    assert(it == "Now")
                    job.cancel()
                }
            }
        }
        job.join()
    }
}