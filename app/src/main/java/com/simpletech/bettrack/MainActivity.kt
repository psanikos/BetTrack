package com.simpletech.bettrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.simpletech.bettrack.feature.home.HomeScreen
import com.simpletech.bettrack.ui.theme.BetTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetTrackTheme {
                HomeScreen()
            }
        }
    }
}