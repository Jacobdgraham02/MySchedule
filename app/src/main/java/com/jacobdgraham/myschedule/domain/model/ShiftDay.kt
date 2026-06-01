package com.jacobdgraham.myschedule.domain.model

/**
 * Represents a given day
 *
 * [ShiftDay] is a domain model class used by this application's business and UI layers and separate from the local Room database or any Room entities
 *
 * @property date the date of this shift day, formatted as yyyy-mm-dd. For example, 2026-05-20
 * @property shiftCode the shift code for this day, such as 2110, or null. Null will be used for when there is no shift on that date
 */
data class ShiftDay (
    val date: String,
    val shiftCode: String?
)