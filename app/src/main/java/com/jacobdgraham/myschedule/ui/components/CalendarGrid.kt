package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.stringArrayResource
import com.jacobdgraham.myschedule.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jacobdgraham.myschedule.domain.model.MonthSchedule
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * This displays a monthly calendar grid
 *
 * [CalendarGrid] arranges the days in [monthSchedule] into a 7-column layout, similar to a physical calendar
 *
 * Empty cells are added before the first day of the month so that each date appears under the correct weekday
 *
 * @param monthSchedule the schedule data for the month being displayed
 * @param modifier optional modifier for any styling and layouts
 */
@Composable
fun CalendarGrid(monthSchedule: MonthSchedule, modifier: Modifier = Modifier) {
    val yearMonth = YearMonth.of (
        monthSchedule.year,
        monthSchedule.month
    )

    val firstDayOfMonth = yearMonth.atDay(1)

    val initialCalendarDays = when (firstDayOfMonth.dayOfWeek) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
    }

    val daysByDate = monthSchedule.days.associateBy { it.date }

    val calendarCells = buildList {
        repeat(initialCalendarDays) {
            add(null)
        }

        for (day in 1..yearMonth.lengthOfMonth()) {
            val date = yearMonth.atDay(day).toString()
            add(daysByDate[date])
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        WeekdayHeader()

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxSize()
        ) {
            items(calendarCells) {
                shiftDay ->
                if (shiftDay == null) {
                    EmptyCalendarCell()
                } else {
                    CalendarDayCell(shiftDay = shiftDay)
                }
            }
        }
    }
}

/**
 * Helper function that lays out a header at the top of calendar which shows the days of the week
*/
@Composable
private fun WeekdayHeader() {
    val weekdayNames = stringArrayResource(R.array.weekday_short_names)
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        weekdayNames.forEach { dayName ->
            Text(
                text = dayName,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Helper function that lays out the display for any empty calendar cell (one which does have any shift data, and only day number)
 */
@Composable
private fun EmptyCalendarCell() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    )
}