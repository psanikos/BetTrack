package com.simpletech.bettrack.feature.home.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.simpletech.bettrack.R
import com.simpletech.bettrack.tools.TestTags
import com.simpletech.bettrack.ui.theme.BetTrackTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    MediumTopAppBar(
        modifier = Modifier.testTag(TestTags.AppBar),
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        )
    )
}

@Preview
@Composable
fun AppBarPreview() {
    BetTrackTheme {
        AppBar()
    }
}