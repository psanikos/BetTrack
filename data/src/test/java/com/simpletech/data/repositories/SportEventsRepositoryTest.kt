package com.simpletech.data.repositories

import com.simpletech.data.api.services.SportsApi
import com.simpletech.data.mappers.getEventsForCategory
import com.simpletech.data.responses.sportEventsSuccessResponse
import com.simpletech.domain.models.SportCategory
import com.simpletech.domain.repositories.SportEventsRepository
import com.simpletech.domain.utils.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class SportEventsRepositoryTest {
    private lateinit var repository: SportEventsRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofit: Retrofit
    private lateinit var service: SportsApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        createService()
        initRepo()
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun fetchSportEventsApiSuccess() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(sportEventsSuccessResponse)
        mockWebServer.enqueue(mockResponse)
        val response = service.getSportsData()
        assert(response.isSuccessful)
        if (response.isSuccessful) {
            assert(response.body()?.isNotEmpty() == true)
            assert(response.body()?.firstOrNull()?.activeEvents?.isNotEmpty() == true)
        }
    }

    @Test
    fun fetchSportEventsApiFailure() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("")
        mockWebServer.enqueue(mockResponse)
        val response = service.getSportsData()
        assert(!response.isSuccessful)
    }

    @Test
    fun fetchSportEventsSuccess() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(sportEventsSuccessResponse)
        mockWebServer.enqueue(mockResponse)
        val response = repository.fetchSportEvents()
        assert(response is ApiResponse.Success)
        if (response is ApiResponse.Success) {
            assert(response.data.sports.isNotEmpty())
            val data = response.data
            with(data) {
                val footballCount = getEventsForCategory(SportCategory.FOOTBALL).size
                val basketBallCount = getEventsForCategory(SportCategory.BASKETBALL).size
                val tennisCount = getEventsForCategory(SportCategory.TENNIS).size
                val tableTennisCount = getEventsForCategory(SportCategory.TABLE_TENNIS).size
                val volleyballCount = getEventsForCategory(SportCategory.VOLLEYBALL).size
                val eSportsCount = getEventsForCategory(SportCategory.ESPORTS).size
                val iceHockeyCount = getEventsForCategory(SportCategory.ICE_HOCKEY).size
                val handballCount = getEventsForCategory(SportCategory.HANDBALL).size
                val snookerCount = getEventsForCategory(SportCategory.SNOOKER).size
                val futsCount = getEventsForCategory(SportCategory.FUTSAL).size

                assert(
                    footballCount == 36 &&
                            basketBallCount == 6 &&
                            tennisCount == 31 &&
                            tableTennisCount == 15 &&
                            volleyballCount == 4 &&
                            eSportsCount == 3 &&
                            iceHockeyCount == 1 &&
                            handballCount == 1 &&
                            snookerCount == 1 &&
                            futsCount == 1
                )
            }
        }
    }

    @Test
    fun fetchSportEventsError() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("")
        mockWebServer.enqueue(mockResponse)
        val response = repository.fetchSportEvents()
        assert(response is ApiResponse.Failure)
    }


    private fun createService() {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl(mockWebServer.url("/"))
            .build()
        service = retrofit.create(SportsApi::class.java)
    }

    private fun initRepo() {
        repository = SportEventsRepositoryImpl(service)
    }
}