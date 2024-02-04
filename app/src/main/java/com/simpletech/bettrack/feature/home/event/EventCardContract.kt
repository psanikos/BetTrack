package com.simpletech.bettrack.feature.home.event

import com.simpletech.bettrack.base.UiEffect
import com.simpletech.bettrack.base.UiEvent
import com.simpletech.bettrack.base.UiState

object EventCardContract {
    data class EventCardState(
        override var isLoading: Boolean = false,
        val isFavourite: Boolean = false
    ) : UiState

    sealed class EventCardEvent : UiEvent {
        data class OnToggleEventSaved(val id: String) : EventCardEvent()
    }

    sealed class EventCardEffect : UiEffect

}