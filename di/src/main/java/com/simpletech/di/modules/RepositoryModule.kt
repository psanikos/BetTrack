package com.simpletech.di.modules

import com.simpletech.data.repositories.SportEventsRepositoryImpl
import com.simpletech.domain.repositories.SportEventsRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Reusable
    fun provideSportEventsRepository(impl: SportEventsRepositoryImpl): SportEventsRepository = impl

}