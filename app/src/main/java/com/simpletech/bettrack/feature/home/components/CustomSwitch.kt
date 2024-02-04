package com.simpletech.bettrack.feature.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simpletech.bettrack.ui.theme.AppColors
import com.simpletech.bettrack.ui.theme.BetTrackTheme

@Composable
fun CustomSwitch(
    icon: ImageVector,
    isEnabled: Boolean,
    onTap: () -> Unit
) {
    val offset = animateFloatAsState(
        targetValue = if (isEnabled) 12f else -12f,
        label = ""
    )

    val color = animateColorAsState(
        targetValue = if (isEnabled) AppColors.Blue else Color.LightGray,
        label = ""
    )

    val backColor = animateColorAsState(
        targetValue = if (isEnabled) Color.DarkGray else Color.Gray,
        label = ""
    )

    Box(
        modifier = Modifier
            .width(50.dp)
            .height(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(18.dp)
                .background(
                    backColor.value,
                    shape = RoundedCornerShape(30.dp)
                )
        )
        Box(
            modifier = Modifier
                .offset(x = offset.value.dp)
                .size(22.dp)
                .background(
                    color.value,
                    shape = CircleShape
                )
                .clickable {
                    onTap()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(18.dp),
                imageVector = icon,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Preview(name = "Switch enabled")
@Composable
fun CustomSwitchPreview() {
    BetTrackTheme {
        CustomSwitch(
            icon = Icons.Filled.Star,
            isEnabled = true
        ) {

        }
    }
}

@Preview(name = "Switch disabled")
@Composable
fun CustomSwitchDisabledPreview() {
    BetTrackTheme {
        CustomSwitch(
            icon = Icons.Filled.Star,
            isEnabled = false
        ) {

        }
    }
}