package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jacobdgraham.myschedule.ui.state.CalendarUiState
import com.jacobdgraham.myschedule.R

/**
 * Reusable function that displays one of a few things:
 * ```
 * 1. If the calendar ui is currently loading, there is a circular loading progress indicator on the screen to indicate that.
 * 2. If there is some sort of error when trying to display the calendar, an error message is shown where the calendar should be
 * 3. If there are no errors and the ui loads successfully, it will be displayed
 * 4. If none of the options above happen, there will be a text message replacing the calendar which informs the user that there is no schedule availability
 * for this month
 */
@Composable
fun CalendarContent (
    uiState: CalendarUiState,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
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
                modifier = modifier
            )
        }

        else -> {
            Text(
                text = stringResource(R.string.no_schedule_availability),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    }
}