package com.simpletech.bettrack.feature.home.mocks

import com.simpletech.domain.models.EventDomainModel
import com.simpletech.domain.models.SportCategory
import com.simpletech.domain.models.SportEventsDomainModel
import com.simpletech.domain.models.SportsEventsDomainModel
import com.simpletech.domain.repositories.SportEventsRepository
import com.simpletech.domain.utils.ApiResponse
import java.util.Date
import javax.inject.Inject

class MockSportEventsRepository @Inject constructor() : SportEventsRepository {
    override suspend fun fetchSportEvents(): ApiResponse<SportsEventsDomainModel> {
        return ApiResponse.Success(
            SportsEventsDomainModel(
                listOf(
                    SportEventsDomainModel(
                        SportCategory.FOOTBALL,
                        "Football",
                        activeEvents = listOf(
                            EventDomainModel(
                                id = "1",
                                teams = "Team 1 - Team 2",
                                category = SportCategory.FOOTBALL,
                                startTime = Date(),
                            ),
                            EventDomainModel(
                                id = "2",
                                teams = "Team 2 - Team 3",
                                category = SportCategory.FOOTBALL,
                                startTime = Date(),
                            )
                        )
                    ),
                    SportEventsDomainModel(
                        SportCategory.BASKETBALL,
                        "Basketball",
                        activeEvents = listOf(
                            EventDomainModel(
                                id = "3",
                                teams = "Team 4 - Team 5",
                                category = SportCategory.BASKETBALL,
                                startTime = Date(),
                            ),
                            EventDomainModel(
                                id = "4",
                                teams = "Team 6 - Team 7",
                                category = SportCategory.BASKETBALL,
                                startTime = Date(),
                            )
                        )
                    )
                )
            )
        )
    }

}