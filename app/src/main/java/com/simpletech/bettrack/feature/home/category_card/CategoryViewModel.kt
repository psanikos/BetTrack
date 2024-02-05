package com.simpletech.bettrack.feature.home.category_card

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.simpletech.bettrack.base.BaseViewModel
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardEffect
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardEvent
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract.CategoryCardState
import com.simpletech.bettrack.tools.DataStoreKeys.SAVED_EVENTS_KEY
import com.simpletech.bettrack.tools.get
import com.simpletech.bettrack.tools.savedEventsDataStore
import com.simpletech.bettrack.tools.set
import com.simpletech.domain.models.SportEventsDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    @ApplicationContext context: Context
) : BaseViewModel<CategoryCardState, CategoryCardEvent, CategoryCardEffect>(
    CategoryCardState()
) {
    private val savedEventsStore = context.savedEventsDataStore
    private val savedEventsFlow = savedEventsStore.get(SAVED_EVENTS_KEY)
        .onEach {
            it?.let {
                updateSavedEvents(it.split(","))
            } ?: updateSavedEvents(listOf())
        }

    init {
        savedEventsFlow.launchIn(viewModelScope)
    }

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
        storeEvents(events.toList())
    }

    private fun storeEvents(events: List<String>) {
        viewModelScope.launch {
            savedEventsStore.set(SAVED_EVENTS_KEY, events.joinToString(","))
        }
    }

    private suspend fun updateSavedEvents(events: List<String>) {
        withContext(Dispatchers.Main) {
            setState {
                copy(
                    savedEvents = events
                )
            }
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