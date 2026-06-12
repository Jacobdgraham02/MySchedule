package com.jacobdgraham.myschedule.data.repository

import android.util.Log
import com.jacobdgraham.myschedule.data.local.IShiftDao
import com.jacobdgraham.myschedule.data.local.ShiftEntity
import com.jacobdgraham.myschedule.data.remote.FirestoreShiftDataSource
import com.jacobdgraham.myschedule.data.repository.interfaces.IShiftRepository
import java.time.YearMonth

/**
 * Provides functions that translate data retrieved from firebase into usable [ShiftEntity] objects
 * @param shiftDao depending on which function is called, a different SQL query will be used (defined in [IShiftDao])
 *      - Example: The below SQL query will be executed when the function [getShiftsForMonth] is called from this repository
 *      ```
 *      @Query("SELECT * FROM shifts WHERE date LIKE :monthPrefix || '%' ORDER BY date ASC")
 *      ```
 *
 */
class ShiftRepository(private val shiftDao: IShiftDao, private val firestoreShiftDataSource: FirestoreShiftDataSource): IShiftRepository {
    private val logcatTag: String = "ShiftRepository"
    /**
     * @param monthPrefix the string month prefix with the year and month, in the following format: xxxx-xx. For example, 2026-05
     * @return List of [ShiftEntity], where each entity shows the current shift for that day and the shift worked that day
     */
    override suspend fun getShiftsForMonth(yearMonth: YearMonth): List<ShiftEntity> {
        // return shiftDao.getShiftsForMonth(monthPrefix)
        val monthPrefix = yearMonth.toString()

        val cachedShifts = shiftDao.getShiftsForMonth(monthPrefix)

        return try {
            val remoteShifts =  firestoreShiftDataSource.getShiftsForMonth(yearMonth)
            shiftDao.deleteShiftsForMonth(monthPrefix)
            shiftDao.upsertShifts(remoteShifts)

            remoteShifts
        } catch (exception: Exception) {
            Log.e(
                logcatTag,
                "Failed to refresh shifts from Firestore for $yearMonth, using cached Room data",
                exception
            )
            cachedShifts
        }
    }

    /**
     * @param monthPrefix the string month prefix with the year and month, in the following format: xxxx-xx. For example, 2026-05
     * @param shifts a list of [ShiftEntity], where each entity has a key value of year and month in xxxx-xx format, and value in the form of shift time xxxx.
     * For example, 2110 to represent start time of 9 pm and 10 hours long
     */
    override suspend fun saveShiftsForMonth(monthPrefix: String, shifts: List<ShiftEntity>) {
        shiftDao.deleteShiftsForMonth(monthPrefix)
        shiftDao.upsertShifts(shifts)
    }
//
//    /**
//     * @param monthPrefix the string month prefix with the year and month, in the following format: xxxx-xx. For example, 2026-05
//     */
    override suspend fun deleteShiftsForMonth(monthPrefix: String) {
        shiftDao.deleteShiftsForMonth(monthPrefix)
    }
}