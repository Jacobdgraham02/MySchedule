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