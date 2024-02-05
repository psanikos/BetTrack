package com.simpletech.bettrack.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simpletech.bettrack.extensions.showToast
import com.simpletech.bettrack.feature.home.category_card.CategoryCard
import com.simpletech.bettrack.feature.home.components.AppBar
import com.simpletech.bettrack.feature.home.components.LoadingIndicator
import com.simpletech.bettrack.tools.TestTags
import com.simpletech.domain.models.SportEventsDomainModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun HomeScreen() {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state().collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        viewModel.effect()
            .distinctUntilChanged()
            .collectLatest {
                when (it) {
                    is HomeScreenContract.HomeEffect.OnError -> {
                        context.showToast(it.message)
                    }
                }
            }
    })
    Content(
        isLoading = state.isLoading,
        data = state.data.orEmpty()
    )
}


@Composable
private fun Content(
    isLoading: Boolean,
    data: List<SportEventsDomainModel>
) {
    Scaffold(
        topBar = {
            AppBar()
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(targetState = isLoading, label = "") { loading ->
                when (loading) {
                    true -> LoadingIndicator()
                    else -> when (data.isEmpty()) {
                        true -> EmptyState()
                        false -> LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(data) { item ->
                                CategoryCard(data = item)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .testTag(TestTags.EmptyState)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No events found")
    }
}