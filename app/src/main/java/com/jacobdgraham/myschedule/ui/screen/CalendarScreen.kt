package com.jacobdgraham.myschedule.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jacobdgraham.myschedule.ui.components.CalendarGrid
import com.jacobdgraham.myschedule.ui.components.FooterBar
import com.jacobdgraham.myschedule.ui.components.ShiftLegend
import com.jacobdgraham.myschedule.ui.preview.createFakeJuneSchedule
import com.jacobdgraham.myschedule.ui.state.CalendarUiState
import java.time.Month
import java.time.YearMonth

/**
 * Main screen for displaying when application is launched for the monthly shift calendar display
 *
 * [CalendarScreen] displays the selected month, the shift legend, main calendar grid space, and footer navigation buttons
 *
 * Receives [CalendarUiState], which is a data class itself, and callback functions from a ViewModel or parent composable
 *
 * @param uiState the current state of teh calendar screen
 * @param onPreviousMonth called when the user taps the previous month button to go to the month prior to the current one
 * @param onToday called when the user taps the button to see the current month
 * @param onNextMonth called when the user taps the next month button
 * @param modifier optional modifier for styling and layout
 */
@Composable
fun CalendarScreen(uiState: CalendarUiState, onPreviousMonth: () -> Unit, onToday: () -> Unit, onNextMonth: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(28.dp))

        ShiftLegend(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formatMonthTitle(uiState.selectedMonth),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            uiState.monthSchedule != null -> {
                CalendarGrid(
                    monthSchedule = uiState.monthSchedule,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }

            else -> {
                Text(
                    text = "No schedule availability for this month",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        FooterBar(
            onPreviousMonth = onPreviousMonth,
            onToday = onToday,
            onNextMonth = onNextMonth,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Temporary test version of the calendar screen.
 *
 * This is useful before the real ViewModel, Room database, or Firestore data
 * source is connected.
 */
@Composable
fun TestCalendarScreen() {
    val fakeSchedule = remember {
        createFakeJuneSchedule()
    }

    CalendarScreen(
        uiState = CalendarUiState(
            selectedMonth = YearMonth.of(2026, 6),
            monthSchedule = fakeSchedule,
        ),
        onPreviousMonth = { },
        onToday = { },
        onNextMonth = { }
    )
}


/**
 * Helper function to format the calendar title in the header of the screen to look user friendly
 */
private fun formatMonthTitle(yearMonth: YearMonth): String {
    val monthName = yearMonth.month.name
        .lowercase()
        .replaceFirstChar {it.uppercase()}

    return "$monthName ${yearMonth.year}"
}