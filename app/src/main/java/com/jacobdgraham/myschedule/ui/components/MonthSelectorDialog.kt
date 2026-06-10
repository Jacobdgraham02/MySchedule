package com.jacobdgraham.myschedule.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
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

    val screenOrientation = LocalConfiguration.current
    val isLandscapeOrientation = screenOrientation.orientation == Configuration.ORIENTATION_LANDSCAPE

    val scrollState = rememberScrollState()

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
                        .verticalScroll(scrollState)
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

                if (isLandscapeOrientation) {
                    LandscapeMonthSelectorRow(
                        selectedMonthValue = selectedMonthValue,
                        onMonthClicked = { monthValue ->
                            selectedMonthValue = monthValue
                        }
                    )
                } else {
                    PortraitMonthSelectorGrid(
                        selectedMonthValue = selectedMonthValue,
                        onMonthClicked = { monthValue ->
                            selectedMonthValue = monthValue
                        }
                    )
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

/**
 * Helper function that builds a composable row that will display the months that you are able to select from in row format
 *
 * @param selectedMonthValue number representation for month from 01 to 12
 * @param onMonthClicked callback function that executes when a month is selected
 */
@Composable
private fun LandscapeMonthSelectorRow(
    selectedMonthValue: Int,
    onMonthClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MonthChips(
            selectedMonthValue = selectedMonthValue,
            onMonthClicked = onMonthClicked
        )
    }
}

/**
 * Helper function that makes a composable grid that will display the months that you are able to select from in a grid format, 4x3
 *
 * @param selectedMonthValue number representation for month from 01 to 12
 * @param onMonthClicked callback function that executes when a month is selected
 */
@Composable
private fun PortraitMonthSelectorGrid(
    selectedMonthValue: Int,
    onMonthClicked: (Int) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MonthChips(
            selectedMonthValue = selectedMonthValue,
            onMonthClicked = onMonthClicked
        )
    }
}

/**
 * Helper function that interactable buttons that you can click to load the data for a given month
 *
 * @param selectedMonthValue number representation for month from 01 to 12
 * @param onMonthClicked callback function that executes when a month is selected
 */
@Composable
private fun MonthChips(
    selectedMonthValue: Int,
    onMonthClicked: (Int) -> Unit
) {
    val locale = LocalLocale.current.platformLocale

    Month.entries.forEach { month ->
        val isSelected = month.value == selectedMonthValue

        FilterChip(
            selected = isSelected,
            onClick = {
                onMonthClicked(month.value)
            },
            label = {
                Text(
                    text = month.getDisplayName(
                        TextStyle.SHORT,
                        locale
                    )
                )
            }
        )
    }
}