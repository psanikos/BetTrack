package com.simpletech.bettrack.feature.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.simpletech.bettrack.MainActivity
import com.simpletech.bettrack.tools.TestTags
import com.simpletech.bettrack.ui.theme.BetTrackTheme
import com.simpletech.di.modules.NetworkModule
import com.simpletech.di.modules.RepositoryModule
import com.simpletech.domain.repositories.SportEventsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(RepositoryModule::class, NetworkModule::class)
@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: SportEventsRepository

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            BetTrackTheme {
                HomeScreen()
            }
        }
    }

    @Test
    fun allItemsVisibleTest() = runTest {
        with(composeRule) {
            onNodeWithTag(TestTags.AppBar).isDisplayed()
            onNodeWithTag(TestTags.EmptyState).isNotDisplayed()
            onAllNodesWithTag(TestTags.CategoryCard).assertCountEquals(2) // Init state as expected
        }
    }

    @Test
    fun categoriesExistTest() = runTest {
        with(composeRule) {
            onNodeWithTag(TestTags.EmptyState).isNotDisplayed()
            onNodeWithText("Football").isDisplayed()
            onNodeWithText("Basketball").isDisplayed() // All categories from data loaded
        }
    }

    @Test
    fun expandCategoryTest() = runTest {
        with(composeRule) {
            onNodeWithTag(TestTags.EmptyState).isNotDisplayed()
            onNodeWithTag(TestTags.ExpandEvents + "Football").isDisplayed()
            onNodeWithTag(TestTags.EventGrid).isNotDisplayed() // Category is displayed but not grid
            onNodeWithTag(TestTags.ExpandEvents + "Football").performClick() // Click expand
            onNodeWithTag(TestTags.EventGrid).isDisplayed() // Grid visible
        }
    }

    @Test
    fun saveEventAndFilterTest() = runTest {
        with(composeRule) {
            onNodeWithTag(TestTags.EmptyState).isNotDisplayed()
            onNodeWithTag(TestTags.ExpandEvents + "Football").performClick()
            onNodeWithTag(TestTags.EventGrid).isDisplayed()
            onAllNodesWithTag(TestTags.EventCard).assertCountEquals(2) // Loaded 2 events
            onNodeWithTag(
                TestTags.EventCardSave + 1,
                useUnmergedTree = true
            ).performClick() // Saved 1
            onAllNodesWithTag(TestTags.EventCard).assertCountEquals(1)
            onNodeWithTag(TestTags.FilterOnlySaved + "Football").performClick() // Filter to show only saved
            onAllNodesWithTag(TestTags.EventCard).assertCountEquals(0) // No not saved events visible
            onAllNodesWithTag(TestTags.EventCardIsSaved).assertCountEquals(1) // 1 Saved event visible
        }
    }
}


