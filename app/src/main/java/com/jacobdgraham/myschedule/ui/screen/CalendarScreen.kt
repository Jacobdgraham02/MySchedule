package com.jacobdgraham.myschedule.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.jacobdgraham.myschedule.ui.components.MonthSelectorDialog
import com.jacobdgraham.myschedule.ui.preview.createFakeMonthSchedule
import com.jacobdgraham.myschedule.ui.state.CalendarUiState
import java.time.YearMonth

/**
 * Main screen for displaying when application is launched for the monthly shift calendar display
 *
 * [CalendarScreen] displays the selected month, the shift legend, main calendar grid space, and footer (empty space currently). Reacts dynamically to
 * portrait or landscape orientation of screen
 *
 * Receives [CalendarUiState], which is a data class itself, and callback functions from a ViewModel or parent composable
 *
 * @param uiState the current state of the calendar screen
 * @param onMonthSelected callback function that is executed whenever a new month is selected. This updates the calendar screen with new data
 * @param modifier optional modifier for styling and layout
 */
@Composable
fun CalendarScreen(uiState: CalendarUiState, onMonthSelected: (YearMonth) -> Unit, modifier: Modifier = Modifier) {
    var isMonthSelectorOpen by remember {
        mutableStateOf(false)
    }

    val screenOrientation = LocalConfiguration.current

    val isPortrait = screenOrientation.orientation == Configuration.ORIENTATION_PORTRAIT

    Text(text = "The variable value is: $isPortrait")

    if (isPortrait) {
        PortraitCalendarLayout(
            uiState = uiState,
            onMonthTitleClicked = {
                isMonthSelectorOpen = true
            },
            modifier = modifier
        )
    } else {
        LandscapeCalendarLayout(
            uiState = uiState,
            onMonthTitleClicked = {
                isMonthSelectorOpen = true
            },
            modifier = Modifier
        )
    }

    if (isMonthSelectorOpen) {
        MonthSelectorDialog(
            currentlySelectedMonth = uiState.selectedMonth,
            onMonthSelected = { selectedMonth ->
                isMonthSelectorOpen = false
                onMonthSelected(selectedMonth) },
            onDismiss = {
                isMonthSelectorOpen = false
            }
        )
    }
}

/**
 * Temporary test version for the calendar screen before Firebase and Room are functional. For a given month that is selected, random data is populated
 *
 * This is useful before the real ViewModel, Room database, or Firestore data
 * source is connected
 */
@Composable
fun TestCalendarScreen() {
    var selectedMonth by remember {
        mutableStateOf(YearMonth.of(2026,6))
    }

    val fakeSchedule = remember(selectedMonth) {
        createFakeMonthSchedule(selectedMonth)
    }

    CalendarScreen(
        uiState = CalendarUiState(
            selectedMonth = selectedMonth,
            monthSchedule = fakeSchedule,
            isLoading = false,
            errorMessage = null
        ),
        onMonthSelected = { newMonth ->
            selectedMonth = newMonth
        }
    )
}


/**
 * Helper function to format the calendar title in the header of the screen to look user friendly
 */
fun formatMonthTitle(yearMonth: YearMonth): String {
    val monthName = yearMonth.month.name
        .lowercase()
        .replaceFirstChar {it.uppercase()}

    return "$monthName ${yearMonth.year}"
}