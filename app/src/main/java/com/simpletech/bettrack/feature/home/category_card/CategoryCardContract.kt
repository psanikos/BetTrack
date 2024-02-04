package com.simpletech.bettrack.feature.home.category_card

import com.simpletech.bettrack.base.UiEffect
import com.simpletech.bettrack.base.UiEvent
import com.simpletech.bettrack.base.UiState
import com.simpletech.domain.models.SportCategory

object CategoryCardContract {
    data class CategoryCardState(
        override var isLoading: Boolean = false,
        val isExpanded: Boolean = false,
        val isFavourite: Boolean = false
    ) : UiState

    sealed class CategoryCardEvent : UiEvent {
        data class OnToggleCategorySaved(val category: SportCategory) : CategoryCardEvent()
        data object OnExpandCategoryToggle : CategoryCardEvent()
    }

    sealed class CategoryCardEffect : UiEffect

}