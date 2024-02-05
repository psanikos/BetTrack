package com.simpletech.bettrack.feature.home

import androidx.lifecycle.viewModelScope
import com.simpletech.bettrack.base.BaseViewModel
import com.simpletech.domain.usecases.FetchSportEventsUseCase
import com.simpletech.domain.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val fetchSportEventsUseCase: FetchSportEventsUseCase
) : BaseViewModel<HomeScreenContract.HomeState, HomeScreenContract.HomeEvent, HomeScreenContract.HomeEffect>(
    HomeScreenContract.HomeState()
) {
    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true) }
            when (val response = fetchSportEventsUseCase()) {
                is ApiResponse.Success -> {
                    Timber.tag(TAG).i("Got data! Count: ${response.data.sports.size}")
                    setState {
                        copy(
                            data = response.data.sports,
                            isLoading = false
                        )
                    }
                }

                is ApiResponse.Failure -> {
                    Timber.tag(TAG).e("Error!: ${response.error.message}")
                    setState { copy(isLoading = false) }
                    showError(response.error)
                }
            }
        }
    }

    private fun showError(exception: Exception) {
        setEffect {
            HomeScreenContract.HomeEffect.OnError(exception.message ?: "Something went wrong")
        }
    }


    companion object {
        const val TAG = "HOMEVM"
    }
}