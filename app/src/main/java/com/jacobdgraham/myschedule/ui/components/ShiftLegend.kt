package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Displays a legend at top of application explaining the shift codes shown in the calendar cells
 *
 * [ShiftLegend] helps the user understand what each calendar cell and corresponding data inside the cell and what it means
 *
 * Examples below:
 * ```
 * 2110 = 8:00 p.m. to 6:00 a.m.
 * 1510 = 3:00 p.m. to 1:00 a.m.
 * 0610 = 6:00 a.m. to 4:00 p.m.
 * ```
 *
 * @param modifier optional modifier for styling and layout
 */
@Composable
fun ShiftLegend(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(12.dp)) {
        Text(text = "Shift Legend",
            style = MaterialTheme.typography.titleSmall)

        LegendRow(shiftCode = "2110", description = "8:00 p.m. to 6:00 a.m.")
        LegendRow(shiftCode = "1510", description = "3:00 p.m. to 1:00 a.m.")
        LegendRow(shiftCode = "0610", description = "6:00 a.m. to 4:00 p.m.")
    }
}

/**
 * Helper function to define what a row would look like in the top header of the application (where it shows a Legend with shift codes)
 * @param shiftCode the shift code (e.g., 2110)
 * @param description the description for the shift code (e.g., 8:00 p.m. - 6:00 a.m.)
 */
@Composable
private fun LegendRow(shiftCode: String, description: String) {
    Row(modifier = Modifier.padding(top = 4.dp)) {
        Text(text = shiftCode, style=MaterialTheme.typography.bodyMedium, color= MaterialTheme.colorScheme.primary)
    }

    Spacer(modifier=Modifier.width(8.dp))

    Text(text = description, style = MaterialTheme.typography.bodyMedium)
}