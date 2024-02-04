package com.simpletech.domain.models

import java.util.Date

data class SportsEventsDomainModel(
    val sports: List<SportEventsDomainModel>
)


data class SportEventsDomainModel(
    val category: SportCategory,
    val title: String,
    val activeEvents: List<EventDomainModel>
)

data class EventDomainModel(
    val id: String,
    val name: String,
    val category: SportCategory,
    val startTime: Date?
)