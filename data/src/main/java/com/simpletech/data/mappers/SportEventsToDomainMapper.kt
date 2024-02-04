package com.simpletech.data.mappers

import com.simpletech.data.api.responses.EventDto
import com.simpletech.data.api.responses.SportEventDto
import com.simpletech.data.helpers.epochSecondsToDate
import com.simpletech.data.helpers.orEmpty
import com.simpletech.domain.models.EventDomainModel
import com.simpletech.domain.models.SportCategory
import com.simpletech.domain.models.SportEventsDomainModel
import com.simpletech.domain.models.SportsEventsDomainModel

fun List<SportEventDto>.toDomainModel(): SportsEventsDomainModel {
    return SportsEventsDomainModel(
        sports = filter { it.sportCategory != null }
            .map {
                SportEventsDomainModel(
                    category = SportCategory.fromCode(it.sportCategory!!),
                    title = it.sportName.orEmpty(),
                    activeEvents = it.activeEvents?.toDomainModel().orEmpty()
                )
            }
    )
}


fun List<EventDto>.toDomainModel(): List<EventDomainModel> {
    return filter { it.sportCategory != null }
        .map {
            EventDomainModel(
                id = it.eventId.orEmpty(),
                name = it.eventName.orEmpty(),
                category = SportCategory.fromCode(it.sportCategory!!),
                startTime = it.startTime?.let { seconds -> seconds.epochSecondsToDate() }
            )
        }
}

fun SportsEventsDomainModel.getEventsForCategory(category: SportCategory): List<EventDomainModel> {
    return sports.firstOrNull { it.category == category }?.activeEvents.orEmpty() // Assuming each category is present once
}