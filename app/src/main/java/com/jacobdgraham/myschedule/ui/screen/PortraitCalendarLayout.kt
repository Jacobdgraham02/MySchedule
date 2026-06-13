package com.jacobdgraham.myschedule.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jacobdgraham.myschedule.ui.components.CalendarContent
import com.jacobdgraham.myschedule.ui.components.FooterBar
import com.jacobdgraham.myschedule.ui.components.MonthTitle
import com.jacobdgraham.myschedule.ui.components.ShiftLegend
import com.jacobdgraham.myschedule.ui.state.CalendarUiState
import com.jacobdgraham.myschedule.ui.theme.SoftLightGrayBackground

/**
 * This layout is used when the user orients the phone in portrait orientation. Unlike [LandscapeCalendarLayout], a vertical scrollbar is not added to this page
 * and it is locked in place
 *
 * @param uiState the state of the calendar as it is rendered on the page
 * @param onMonthTitleClicked callback function executed when a new month is selected. This updates state with new data
 * @param modifier optional layout modifier
 */
@Composable
fun PortraitCalendarLayout(
    uiState: CalendarUiState,
    onMonthTitleClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SoftLightGrayBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        ShiftLegend(
            shiftDefinitions = uiState.shiftDefinitions,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        MonthTitle(
            selectedMonth = uiState.selectedMonth,
            onClick = onMonthTitleClicked
        )

        CalendarContent(
            uiState = uiState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        FooterBar(
            modifier = Modifier.fillMaxWidth()
        )
    }
}