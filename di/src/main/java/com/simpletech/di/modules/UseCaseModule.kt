package com.simpletech.di.modules

import com.simpletech.domain.repositories.SportEventsRepository
import com.simpletech.domain.usecases.FetchSportEventsUseCase
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    fun provideSportEventsUseCase(
        repository: SportEventsRepository
    ): FetchSportEventsUseCase = FetchSportEventsUseCase(repository)

}