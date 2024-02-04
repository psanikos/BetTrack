package com.simpletech.domain.usecases

import com.simpletech.domain.models.SportsEventsDomainModel
import com.simpletech.domain.repositories.SportEventsRepository
import com.simpletech.domain.utils.ApiResponse

class FetchSportEventsUseCase(private val repository: SportEventsRepository) {

    suspend operator fun invoke(): ApiResponse<SportsEventsDomainModel> {
        return repository.fetchSportEvents()
    }
}