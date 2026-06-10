package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jacobdgraham.myschedule.ui.state.CalendarUiState

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
                text = "No schedule availability for this month",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    }
}