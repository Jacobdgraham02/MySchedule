package com.jacobdgraham.myschedule.domain.model

/**
 * Represents all shift information for a single month
 *
 * A [MonthSchedule] groups together the shift days that belong to the same year and month
 *
 * @property year the calendar year, such as 2026
 * @property month the calendar month, such as 05. This ranges from 1 to 12
 * @property days the list of days in this month and their shifts
 */
data class MonthSchedule (
    val year: Int,
    val month: Int,
    val days: List<ShiftDay>
)