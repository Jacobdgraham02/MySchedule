package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.jacobdgraham.myschedule.ui.screen.formatMonthTitle
import java.time.YearMonth

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