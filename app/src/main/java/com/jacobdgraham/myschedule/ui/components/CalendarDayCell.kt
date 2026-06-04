package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jacobdgraham.myschedule.domain.model.ShiftDay

/**
 * Represents the look of a single cell in the calendar grid
 *
 * [CalendarDayCell] shows the day number and the shift code for that day if working that day
 *
 * @param shiftDay the shift information for the given calendar day
 * @param modifier any optional modifier used for styling and layout
 */
@Composable
fun CalendarDayCell(shiftDay: ShiftDay, modifier: Modifier = Modifier) {
    val dayNumber = shiftDay.date.takeLast(2).toIntOrNull()?.toString() ?: ""

    Box (
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text (
                text = dayNumber,
                style = MaterialTheme.typography.bodyMedium
            )

            if (shiftDay.shiftCode != null) {
                Text (
                    text = shiftDay.shiftCode,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}