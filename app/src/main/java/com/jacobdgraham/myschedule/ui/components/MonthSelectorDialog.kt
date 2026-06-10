package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import com.jacobdgraham.myschedule.R

/**
 * Dialog that allows the user to select a given month and year. This component is used when the user selects the calendar title, which is underlined as:
 * ```
 * <month> <year>
 * ```
 *
 * The dialog currently displays as:
 * ```
 * Select Month
 *
 * < year >
 *
 * Jan Feb Mar Apr
 * May Jun Jul Aug
 * Sep Oct Nov Dec
 *
 * Cancel    Select
 * ```
 * @param currentlySelectedMonth the current month that is selected, formatted as 01 to 12
 * @param onMonthSelected callback function that is executed when a month is selected
 * @param onDismiss callback function that is executed when the dialog option is closed
 */
@Composable
fun MonthSelectorDialog(currentlySelectedMonth: YearMonth, onMonthSelected: (YearMonth) -> Unit, onDismiss: () -> Unit) {
    var selectedYear by remember {
        mutableIntStateOf(currentlySelectedMonth.year)
    }

    var selectedMonthValue by remember {
        mutableIntStateOf(currentlySelectedMonth.monthValue)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.month_selector_dialog_choose_month_prompt))
        },
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            selectedYear--
                        }
                    ) {
                        Text(text = "‹")
                    }

                    Text(
                        text = selectedYear.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )

                    IconButton(
                        onClick = {
                            selectedYear++
                        }
                    ) {
                        Text(text = "›")
                    }
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Month.entries.forEach { month ->
                        val isSelected = month.value == selectedMonthValue

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedMonthValue = month.value
                            },
                            label = {
                                Text(
                                    text = month.getDisplayName(
                                        TextStyle.SHORT,
                                        LocalLocale.current.platformLocale
                                    )
                                )
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onMonthSelected(
                        YearMonth.of(
                            selectedYear,
                            selectedMonthValue
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.month_selector_dialog_select))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(R.string.month_selector_dialog_cancel))
            }
        }
    )
}