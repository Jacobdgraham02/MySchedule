package com.jacobdgraham.myschedule.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jacobdgraham.myschedule.ui.components.CalendarGrid
import com.jacobdgraham.myschedule.ui.components.FooterBar
import com.jacobdgraham.myschedule.ui.components.ShiftLegend
import com.jacobdgraham.myschedule.ui.state.CalendarUiState

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
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatMonthTitle(uiState),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ShiftLegend()
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
                    modifier = Modifier.weight(1f)
                )
            }

            else -> {
                Text(
                    text = "No schedule availability for this month",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        FooterBar(
            onPreviousMonth = onPreviousMonth,
            onToday = onToday,
            onNextMonth = onNextMonth
        )
    }
}

/**
 * Helper function to format the calendar title in the header of the screen to look user friendly
 */
private fun formatMonthTitle(uiState: CalendarUiState): String {
    val month = uiState.selectedMonth.month.name
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    val year = uiState.selectedMonth.year
    return "$month $year"
}