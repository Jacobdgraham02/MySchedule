package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.jacobdgraham.myschedule.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jacobdgraham.myschedule.domain.model.ShiftDefinition
import com.jacobdgraham.myschedule.ui.theme.getShiftCodeColour

/**
 * Displays a legend at top of application explaining the shift codes shown in the calendar cells. Displays horizontally.
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
fun ShiftLegend(shiftDefinitions: List<ShiftDefinition>, hasAttemptedLoad: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 1.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.shift_legend_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

//        if (shiftDefinitions.isEmpty()) {
//            Text(
//                text = stringResource(R.string.no_shift_data_for_month),
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.error
//            )
//        }
        if (hasAttemptedLoad && shiftDefinitions.isEmpty()) {
            Text(
                text = stringResource(R.string.no_shift_data_for_month),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            FlowRow(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                shiftDefinitions.forEach { shiftDefinition ->
                    LegendItem(
                        shiftCode = shiftDefinition.code,
                        description = shiftDefinition.definition
                    )
                }
            }
        }
    }
}

/**
 * Helper function to define what a row would look like in the top header of the application (where it shows a Legend with shift codes)
 * @param shiftCode the shift code (e.g., 2110)
 * @param description the description for the shift code (e.g., 8:00 p.m. - 6:00 a.m.)
 */
@Composable
private fun LegendItem(
    shiftCode: String,
    description: String
) {
    Row(
        modifier = Modifier.padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = shiftCode,
            style = MaterialTheme.typography.labelSmall,
            color = getShiftCodeColour(shiftCode)
        )

        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
    }
}