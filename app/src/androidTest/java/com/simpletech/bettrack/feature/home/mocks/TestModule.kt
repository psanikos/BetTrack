package com.simpletech.bettrack.feature.home.mocks

import com.simpletech.di.modules.RepositoryModule
import com.simpletech.domain.repositories.SportEventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestModule {
    @Singleton
    @Binds
    abstract fun provideMockRepo(
        repo: MockSportEventsRepository
    ): SportEventsRepository
}