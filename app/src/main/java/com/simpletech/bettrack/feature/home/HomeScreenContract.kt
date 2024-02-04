package com.simpletech.bettrack.feature.home

import com.simpletech.bettrack.base.UiEffect
import com.simpletech.bettrack.base.UiEvent
import com.simpletech.bettrack.base.UiState
import com.simpletech.domain.models.SportEventsDomainModel

object HomeScreenContract {
    data class HomeState(
        override var isLoading: Boolean = false,
        val data: List<SportEventsDomainModel>? = null
    ) : UiState

    sealed class HomeEvent : UiEvent

    sealed class HomeEffect : UiEffect {
        data class OnError(val message: String) : HomeEffect()
    }
}