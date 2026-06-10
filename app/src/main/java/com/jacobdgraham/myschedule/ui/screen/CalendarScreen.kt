package com.jacobdgraham.myschedule.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.jacobdgraham.myschedule.ui.components.CalendarGrid
import com.jacobdgraham.myschedule.ui.components.FooterBar
import com.jacobdgraham.myschedule.ui.components.MonthSelectorDialog
import com.jacobdgraham.myschedule.ui.components.ShiftLegend
import com.jacobdgraham.myschedule.ui.preview.createFakeMonthSchedule
import com.jacobdgraham.myschedule.ui.state.CalendarUiState
import com.jacobdgraham.myschedule.ui.theme.SoftLightGrayBackground
import java.time.YearMonth

/**
 * Main screen for displaying when application is launched for the monthly shift calendar display
 *
 * [CalendarScreen] displays the selected month, the shift legend, main calendar grid space, and footer (empty space currently)
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SoftLightGrayBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(28.dp))

        ShiftLegend(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formatMonthTitle(uiState.selectedMonth),
            style = MaterialTheme.typography.titleMedium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                isMonthSelectorOpen = true
            }
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

        if (isMonthSelectorOpen) {
            MonthSelectorDialog(
                currentlySelectedMonth = uiState.selectedMonth,
                onMonthSelected = { selectedMonth ->
                    isMonthSelectorOpen = false
                    onMonthSelected(selectedMonth)
                },
                onDismiss = {
                    isMonthSelectorOpen = false
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        FooterBar(
            modifier = Modifier.fillMaxWidth()
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
private fun formatMonthTitle(yearMonth: YearMonth): String {
    val monthName = yearMonth.month.name
        .lowercase()
        .replaceFirstChar {it.uppercase()}

    return "$monthName ${yearMonth.year}"
}