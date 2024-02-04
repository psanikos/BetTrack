package com.simpletech.bettrack.feature.home.event

import com.simpletech.bettrack.base.BaseViewModel
import javax.inject.Inject

class EventViewModel @Inject constructor(
) : BaseViewModel<EventCardContract.EventCardState, EventCardContract.EventCardEvent, EventCardContract.EventCardEffect>(
    EventCardContract.EventCardState()
) {

    override fun handleEvent(event: EventCardContract.EventCardEvent) {
        when (event) {
            is EventCardContract.EventCardEvent.OnToggleEventSaved -> onToggleSaveState(event.id)
            else -> {}
        }
    }

    private fun onToggleSaveState(id: String) {
        setState {
            copy(
                isFavourite = !this.isFavourite
            )
        }
    }
}