package com.simpletech.bettrack.feature.home

import com.simpletech.bettrack.base.UiEvent
import com.simpletech.bettrack.base.UiState
import com.simpletech.domain.models.SportsEventsDomainModel

object HomeScreenContract {
    data class HomeState(
        override var isLoading: Boolean = false,
        override var errorMessage: String? = null,
        val data: SportsEventsDomainModel? = null
    ) : UiState

    sealed class HomeEvent : UiEvent
}