package com.simpletech.bettrack.feature.home.category_card

import com.simpletech.bettrack.base.BaseViewModel
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardEffect
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardEvent
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardState
import com.simpletech.domain.models.SportEventsDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
) : BaseViewModel<CategoryCardState, CategoryCardEvent, CategoryCardEffect>(
    CategoryCardState()
) {
    override fun handleEvent(event: CategoryCardEvent) {
        when (event) {
            CategoryCardEvent.OnExpandCategoryToggle -> onExpandToggle()
            is CategoryCardEvent.OnReceivedData -> onReceivedData(event.data)
            is CategoryCardEvent.OnSaveEvent -> onSaveEvent(event.eventId)
            CategoryCardEvent.OnToggleShowOnlySaved -> onFilterOnlyFavourite()
        }
    }


    private fun onReceivedData(data: SportEventsDomainModel) {
        setState {
            copy(
                data = data
            )
        }
    }

    private fun onSaveEvent(id: String) {
        val events = currentState.savedEvents.toMutableSet()
        events.add(id)
        setState {
            copy(
                savedEvents = events.toList()
            )
        }
    }

    private fun onFilterOnlyFavourite() {
        setState {
            copy(
                showOnlyFavourite = !this.showOnlyFavourite
            )
        }
    }

    private fun onExpandToggle() {
        setState {
            copy(
                isExpanded = !this.isExpanded
            )
        }
    }
}