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
) : BaseViewModel<HomeScreenContract.HomeState, HomeScreenContract.HomeEvent>(HomeScreenContract.HomeState()) {

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = fetchSportEventsUseCase()) {
                is ApiResponse.Success -> {
                    Timber.tag(TAG).i("Got data! Count: ${response.data.sports.size}")
                    setState {
                        copy(
                            data = response.data
                        )
                    }
                }

                is ApiResponse.Failure -> {
                    Timber.tag(TAG).e("Error!: ${response.error.message}")
                    showError(response.error)
                }
            }
        }
    }

    private fun showError(exception: Exception) {
        setState {
            copy(
                errorMessage = exception.message ?: "Something went wrong"
            )
        }
        toggleError()
    }


    companion object {
        val TAG = "HOMEVM"
    }
}