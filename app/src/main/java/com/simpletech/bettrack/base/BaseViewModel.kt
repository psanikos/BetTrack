package com.simpletech.bettrack.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UiState, E : UiEvent, F : UiEffect>(
    initialState: S,
) : ViewModel() {

    private val state: MutableStateFlow<S> = MutableStateFlow(initialState)
    fun state(): StateFlow<S> = state
    val currentState: S
        get() = state.value

    private val effect: MutableSharedFlow<F> = MutableSharedFlow()
    fun effect(): SharedFlow<F> = effect

    fun onEvent(event: E) = viewModelScope.launch {
        handleEvent(event)
    }

    protected open fun handleEvent(event: E) {}

    protected fun setState(reduce: S.() -> S) {
        val newState = currentState.reduce()
        state.value = newState
    }

    protected fun setEffect(builder: () -> F) {
        val effectValue = builder()
        viewModelScope.launch { effect.emit(effectValue) }
    }
}
