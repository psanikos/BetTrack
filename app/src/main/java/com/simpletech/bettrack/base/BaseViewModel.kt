package com.simpletech.bettrack.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<S : UiState, E : UiEvent>(
    initialState: S,
) : ViewModel() {

    private val state: MutableStateFlow<S> = MutableStateFlow(initialState)
    fun state(): StateFlow<S> = state
    val currentState: S
        get() = state.value

    val hasError = AtomicBoolean(false)

    protected open suspend fun onEvent(event: E) {}

    protected fun setState(reduce: S.() -> S) {
        val newState = currentState.reduce()
        state.value = newState
    }

    fun toggleError() {
        hasError.compareAndSet(false, true)
    }
}
