package com.jacobdgraham.myschedule.ui.preview

import com.jacobdgraham.myschedule.domain.model.MonthSchedule
import com.jacobdgraham.myschedule.domain.model.ShiftDay
import java.time.YearMonth

/**
 * Creates fake schedule data for testing the calendar UI in the month of June
 *
 * This function generates random data in June 2026 and randomly assigns one of the 4 possible shift codes: 2010, 1510, 0610, or 'null' for when no shift is worked
 */
fun createFakeJuneSchedule(): MonthSchedule {
    val year = 2026
    val month = 6
    val yearMonth = YearMonth.of(year, month)

    val possibleShiftCodes = listOf(
        "2110",
        "1510",
        "0610",
        null
    )

    val days = (1..yearMonth.lengthOfMonth()).map { day ->
        ShiftDay(
            date = yearMonth.atDay(day).toString(),
            shiftCode = possibleShiftCodes[kotlin.random.Random.nextInt(possibleShiftCodes.size)]
        )
    }

    return MonthSchedule(
        year = year,
        month = month,
        days = days
    )
}