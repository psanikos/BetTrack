package com.simpletech.bettrack.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract
import com.simpletech.bettrack.feature.home.event.EventCard
import com.simpletech.bettrack.tools.TestTags
import com.simpletech.bettrack.ui.theme.BetTrackTheme
import com.simpletech.domain.models.EventDomainModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventGrid(
    events: List<Pair<EventDomainModel, Boolean>>,
    onEvent: (CategoryCardContract.CategoryCardEvent) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .testTag(TestTags.EventGrid)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        events.forEach { (event, isSaved) ->
            EventCard(data = event, isSaved, onEvent)
        }
    }
}

@Preview
@Composable
fun EventGridPreview() {
    BetTrackTheme {
        EventGrid(
            events = CategoryCardContract.CategoryCardState.mockData,
            onEvent = {}
        )
    }
}