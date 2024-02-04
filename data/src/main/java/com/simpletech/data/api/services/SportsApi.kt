package com.simpletech.data.api.services

import com.simpletech.data.api.responses.SportEventsDto
import retrofit2.Response
import retrofit2.http.GET

interface SportsApi {
    @GET("/sports")
    suspend fun getSportsData(): Response<SportEventsDto>
}