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
fun LandscapeCalendarLayout(
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
//    Row(
//        modifier = modifier
//            .fillMaxSize()
//            .background(SoftLightGrayBackground),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(
//            modifier = Modifier
//                .width(220.dp)
//                .padding(horizontal = 12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            ShiftLegend(
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            MonthTitle(
//                selectedMonth = uiState.selectedMonth,
//                onClick = onMonthTitleClicked
//            )
//        }
//
//        CalendarContent(
//            uiState = uiState,
//            modifier = Modifier
//                .weight(1f)
//                .padding(end = 12.dp)
//        )
//    }
}