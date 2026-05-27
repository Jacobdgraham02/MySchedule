package com.jacobdgraham.myschedule.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local Room entity representing a single shift
 *
 * Each [ShiftEntity] is stored in the 'shifts' table
 *
 * @property date the unique date for each shift that is worked. I only work a maximum of one time per day, so there will only be 1 shift value for each date
 *
 * @property shiftCode the code representing the type of shift worked on this date. This date will be formatted in 24-hour format, where the first two digits
 * represent the starting shift hour, and the last two digits represent the length of the shift. For example, 2010 represents a shift that starts at 8:00 p.m.
 * and lasts 10 hours
 *
 * Examples:
 *
 * ```
 * date = "2026-05-01", shiftCode = "2010"
 * date = "2026-05-02", shiftCode = "1510"
 * date = "2026-05-03", shiftCode = "0610"
 * ```
 */
@Entity(tableName="shifts")
data class ShiftEntity(
    @PrimaryKey val date: String,
    val shiftCode: String
)