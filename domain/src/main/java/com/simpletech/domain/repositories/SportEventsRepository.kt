package com.simpletech.domain.repositories

import com.simpletech.domain.models.SportsEventsDomainModel
import com.simpletech.domain.utils.ApiResponse

interface SportEventsRepository {
    suspend fun fetchSportEvents(): ApiResponse<SportsEventsDomainModel>
}




