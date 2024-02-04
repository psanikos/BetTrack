package com.simpletech.data.repositories

import com.simpletech.data.api.services.SportsApi
import com.simpletech.data.helpers.ApiException
import com.simpletech.data.mappers.toDomainModel
import com.simpletech.domain.models.SportsEventsDomainModel
import com.simpletech.domain.repositories.SportEventsRepository
import com.simpletech.domain.utils.ApiResponse
import javax.inject.Inject

class SportEventsRepositoryImpl @Inject constructor(
    private val service: SportsApi
) : SportEventsRepository {
    override suspend fun fetchSportEvents(): ApiResponse<SportsEventsDomainModel> {
        return try {
            service.getSportsData().run {
                requireNotNull(body())
                if (isSuccessful) ApiResponse.Success(body()!!.toDomainModel())
                else ApiResponse.Failure(ApiException.EmptyBodyException)
            }
        } catch (e: Exception) {
            ApiResponse.Failure(e)
        }
    }

}

