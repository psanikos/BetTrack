package com.simpletech.bettrack.feature.home

import androidx.lifecycle.ViewModel
import com.simpletech.domain.usecases.FetchSportEventsUseCase
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val fetchSportEventsUseCase: FetchSportEventsUseCase
) : ViewModel()