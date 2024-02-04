package com.simpletech.bettrack.feature.home.category_card

import com.simpletech.bettrack.base.BaseViewModel
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardEffect
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardEvent
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardState
import com.simpletech.domain.models.SportCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
) : BaseViewModel<CategoryCardState, CategoryCardEvent, CategoryCardEffect>(
    CategoryCardState()
) {
    override fun handleEvent(event: CategoryCardEvent) {
        when (event) {
            is CategoryCardEvent.OnToggleCategorySaved -> onSaveChanged(event.category)
            CategoryCardEvent.OnExpandCategoryToggle -> onExpandToggle()
            else -> {}
        }
    }

    private fun onSaveChanged(category: SportCategory) {
        setState {
            copy(
                isFavourite = !this.isFavourite
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