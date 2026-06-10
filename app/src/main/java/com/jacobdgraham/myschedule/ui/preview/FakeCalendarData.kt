package com.jacobdgraham.myschedule.ui.preview

import com.jacobdgraham.myschedule.domain.model.MonthSchedule
import com.jacobdgraham.myschedule.domain.model.ShiftDay
import java.time.YearMonth
import kotlin.random.Random


/**
 * Creates fake schedule data for testing the calendar UI for a given month selected by a dialog selector and randomly assigns
 * one of 4 possible shift codes for each day of that month: 2010, 1510, 0610, or 'null', for when no shift is worked
 */

fun createFakeMonthSchedule(yearMonth: YearMonth): MonthSchedule {
    val possibleShiftCodes = listOf(
        "0610",
        "1510",
        "2010",
        null
    )

    val random: Random = Random(
        seed = yearMonth.year * 100 + yearMonth.monthValue
    )

    val days = (1..yearMonth.lengthOfMonth()).map { day ->
        ShiftDay(
            date = yearMonth.atDay(day).toString(),
            shiftCode = possibleShiftCodes[random.nextInt(possibleShiftCodes.size)]
        )
    }

    return MonthSchedule(
        year = yearMonth.year,
        month = yearMonth.monthValue,
        days = days
    )
}