package com.jacobdgraham.myschedule.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Defines the Room database operators. For this application, we only need Create, Read, and Delete operations, since the application
 * is only meant to show shifts that I work on a given day in a month. Responsible for reading, inserting, replacing, and deleting
 * [ShiftEntity] records in the local 'shifts' table.
 *
 * Application defines one month at a time, so operations use 'monthPrefix', such as '2026-05' to match all
 * shifts in that month
 *
 * Functions:
 *
 * - [getShiftsForMonth]      returns all shifts for a given month sorted ascendingly, starting at first day of month and going until the last day
 * - [upsertShifts]           inserts a list of shifts into the existing shifts, replacing any shift with the same date
 * - [deleteShiftsForMonth]   deletes all shifts for a given month
 */
@Dao
interface ShiftDao {
    @Query("SELECT * FROM shifts WHERE date LIKE :monthPrefix || '%' ORDER BY date ASC")
    suspend fun getShiftsForMonth(monthPrefix: String): List<ShiftEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertShifts(shifts:List<ShiftEntity>)

    @Query("DELETE FROM shifts WHERE date LIKE :monthPrefix || '%'")
    suspend fun deleteShiftsForMonth(monthPrefix: String)
}