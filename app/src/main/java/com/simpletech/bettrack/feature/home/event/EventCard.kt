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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.simpletech.bettrack.feature.home.category_card.CategoryCardContract
import com.simpletech.bettrack.tools.TestTags
import com.simpletech.bettrack.tools.getCountDownFlow
import com.simpletech.bettrack.ui.theme.AppColors
import com.simpletech.domain.models.EventDomainModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EventCard(
    data: EventDomainModel,
    isSaved: Boolean,
    onEvent: (CategoryCardContract.CategoryCardEvent) -> Unit
) {
    var timerText by rememberSaveable {
        mutableStateOf("")
    }

    val starColor = animateColorAsState(
        targetValue = if (isSaved) AppColors.Yellow else Color.LightGray,
        label = ""
    )

    LaunchedEffect(key1 = Unit, block = {
        data.startTime?.let { date ->
            date.getCountDownFlow().collectLatest {
                timerText = it
            }
        }
    })

    Column(
        modifier = Modifier
            .run {
                if (isSaved) testTag(TestTags.EventCardIsSaved)
                else testTag(TestTags.EventCard)
            }
            .height(120.dp)
            .width(100.dp)
            .clickable {
                onEvent(CategoryCardContract.CategoryCardEvent.OnSaveEvent(data.id))
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = timerText,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        Icon(
            modifier = Modifier
                .testTag(TestTags.EventCardSave + data.id)
                .size(20.dp),
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