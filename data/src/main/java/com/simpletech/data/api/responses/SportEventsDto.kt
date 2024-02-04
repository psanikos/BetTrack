package com.simpletech.data.api.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias SportEventsDto = List<SportEventDto>

@JsonClass(generateAdapter = true)
data class SportEventDto(
    @Json(name = "i")
    val sportCategory: String?,
    @Json(name = "d")
    val sportName: String?,
    @Json(name = "e")
    val activeEvents: List<EventDto>?
)

@JsonClass(generateAdapter = true)
data class EventDto(
    @Json(name = "d")
    val eventName: String?,
    @Json(name = "i")
    val eventId: String?,
    @Json(name = "sh")
    val name: String?,
    @Json(name = "si")
    val sportCategory: String?,
    @Json(name = "tt")
    val startTime: Long?
)