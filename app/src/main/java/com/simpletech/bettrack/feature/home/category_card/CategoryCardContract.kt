package com.simpletech.bettrack.feature.home.category_card

import com.simpletech.bettrack.base.UiEffect
import com.simpletech.bettrack.base.UiEvent
import com.simpletech.bettrack.base.UiState
import com.simpletech.domain.models.EventDomainModel
import com.simpletech.domain.models.SportEventsDomainModel

object CategoryCardContract {
    data class CategoryCardState(
        override var isLoading: Boolean = false,
        val isExpanded: Boolean = false,
        val showOnlyFavourite: Boolean = false,
        val data: SportEventsDomainModel? = null,
        val savedEvents: List<String> = listOf()
    ) : UiState {
        val mappedSavedEvents: List<Pair<EventDomainModel, Boolean>>
            get() {
                return data?.activeEvents
                    ?.asSequence()
                    ?.map {
                        it to savedEvents.contains(it.id)
                    }
                    .orEmpty()
                    .filter { if (showOnlyFavourite) it.second else true }
                    .toList()
            }

    }

    sealed class CategoryCardEvent : UiEvent {
        data object OnToggleShowOnlySaved : CategoryCardEvent()
        data object OnExpandCategoryToggle : CategoryCardEvent()
        data class OnSaveEvent(val eventId: String) : CategoryCardEvent()

        data class OnReceivedData(val data: SportEventsDomainModel) : CategoryCardEvent()
    }

    sealed class CategoryCardEffect : UiEffect

}