package com.jacobdgraham.myschedule.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
 * This layout is used when the user orients the phone in landscape orientation. This adds a vertical scrollbar to the screen that is invisible
 * and locks the calendar so the user does not accidentally try and scroll on the calendar, and instead scrolls the entire page instead
 *
 * @param uiState the state of the calendar as it is rendered on the page
 * @param onMonthTitleClicked callback function executed when a new month is selected. This updates state with new data
 * @param modifier optional layout modifier
 */
@Composable
fun LandscapeCalendarLayout(
    uiState: CalendarUiState,
    onMonthTitleClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SoftLightGrayBackground)
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            ShiftLegend(
                shiftDefinitions = uiState.shiftDefinitions,
                hasAttemptedLoad = uiState.hasAttemptedLoad,
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
                    .height(360.dp)
            )

            FooterBar(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}