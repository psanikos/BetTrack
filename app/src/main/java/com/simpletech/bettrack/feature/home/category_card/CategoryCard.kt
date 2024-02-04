package com.simpletech.bettrack.feature.home.category_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simpletech.bettrack.feature.home.components.CustomSwitch
import com.simpletech.bettrack.feature.home.event.EventCard
import com.simpletech.bettrack.ui.theme.AppColors
import com.simpletech.domain.models.EventDomainModel
import com.simpletech.domain.models.SportEventsDomainModel


@Composable
fun CategoryCard(
    data: SportEventsDomainModel
) {
    val viewModel: CategoryViewModel = hiltViewModel(key = data.category.code)
    val state by viewModel.state().collectAsStateWithLifecycle()

    val rotation by animateFloatAsState(
        targetValue = if (state.isExpanded) -180f else 0f,
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(
                            AppColors.Red,
                            shape = CircleShape
                        )
                )
                Text(
                    data.title,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomSwitch(
                    icon = Icons.Filled.Star,
                    isEnabled = state.isFavourite
                ) {
                    viewModel.onEvent(
                        CategoryCardContract.CategoryCardEvent.OnToggleCategorySaved(
                            data.category
                        )
                    )
                }

                IconButton(onClick = {
                    viewModel.onEvent(CategoryCardContract.CategoryCardEvent.OnExpandCategoryToggle)
                }) {
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "",
                        modifier = Modifier.rotate(rotation),
                        tint = Color.Black
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = state.isExpanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            EventGrid(events = data.activeEvents)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventGrid(
    events: List<EventDomainModel>
) {
    FlowRow(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        events.forEach {
            EventCard(data = it)
        }
    }
}