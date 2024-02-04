package com.simpletech.bettrack.feature.home.event

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simpletech.bettrack.ui.theme.AppColors
import com.simpletech.domain.models.EventDomainModel

@Composable
fun EventCard(
    data: EventDomainModel
) {
    val viewModel: EventViewModel = hiltViewModel(key = data.id)
    val state by viewModel.state().collectAsStateWithLifecycle()

    val starColor = animateColorAsState(
        targetValue = if (state.isFavourite) AppColors.Yellow else Color.LightGray,
        label = ""
    )
    Column(
        modifier = Modifier
            .height(120.dp)
            .width(100.dp)
            .clickable {
                viewModel.onEvent(EventCardContract.EventCardEvent.OnToggleEventSaved(data.id))
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            "HH:MM:SS",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Filled.Star,
            contentDescription = "",
            tint = starColor.value
        )
        Text(
            data.teamList.getOrNull(0).orEmpty(),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            "vs",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = AppColors.Red
        )
        Text(
            data.teamList.getOrNull(1).orEmpty(),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }

}