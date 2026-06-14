package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.jacobdgraham.myschedule.ui.screen.formatMonthTitle
import java.time.YearMonth

/**
 * Displays the currently selected month as a clickable title
 *
 * The title is shown above the calendar grid and opens the month and year dialog selected [MonthSelectorDialog] when clicked
 *
 * Example display:
 * ```
 * June 2026
 * ```
 * @param selectedMonth year and month that is displayed on the dialog
 * @param onClick callback function called when the user taps the month title
 */
@Composable
fun MonthTitle(
    selectedMonth: YearMonth,
    onClick: () -> Unit
) {
    Text(
        text = formatMonthTitle(selectedMonth),
        style = MaterialTheme.typography.titleMedium,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}