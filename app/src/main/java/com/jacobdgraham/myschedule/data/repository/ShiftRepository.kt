package com.jacobdgraham.myschedule.data.repository

import android.util.Log
import com.jacobdgraham.myschedule.data.local.IShiftDao
import com.jacobdgraham.myschedule.data.local.IShiftDefinitionDao
import com.jacobdgraham.myschedule.data.local.ShiftEntity
import com.jacobdgraham.myschedule.data.remote.FirestoreShiftDataSource
import com.jacobdgraham.myschedule.data.repository.interfaces.IShiftRepository
import com.jacobdgraham.myschedule.domain.model.ShiftDefinition
import java.time.YearMonth
import com.jacobdgraham.myschedule.data.local.toDomain
import com.jacobdgraham.myschedule.data.local.toEntity
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.milliseconds

/**
 * Provides functions that translate data retrieved from firebase into usable [ShiftEntity] objects
 * @param shiftDao depending on which function is called, a different SQL query will be used (defined in [IShiftDao])
 *      - Example: The below SQL query will be executed when the function [getShiftsForMonth] is called from this repository
 *      ```
 *      @Query("SELECT * FROM shifts WHERE date LIKE :monthPrefix || '%' ORDER BY date ASC")
 *      ```
 *
 */
class ShiftRepository(private val shiftDao: IShiftDao, private val shiftDefinitionDao: IShiftDefinitionDao, val firestoreShiftDataSource: FirestoreShiftDataSource): IShiftRepository {
    private val logcatTag: String = "ShiftRepository"
    /**
     * @param yearMonth the string month prefix with the year and month, in the following format: xxxx-xx. For example, 2026-05
     * @return List of [ShiftEntity], where each entity shows the current shift for that day and the shift worked that day
     */
    override suspend fun getShiftsForMonth(yearMonth: YearMonth): List<ShiftEntity> {
        val monthPrefix = yearMonth.toString()

        val cachedShifts = shiftDao.getShiftsForMonth(monthPrefix)

        val remoteShifts = withTimeoutOrNull(2_000L.milliseconds) {
            try {
                firestoreShiftDataSource.getShiftsForMonth(yearMonth)
            }  catch (exception: Exception) {
                Log.e(
                    logcatTag,
                    "Failed to refresh shifts from Firestore for $yearMonth, using cached Room data",
                    exception
                )
                null
            }
        }

        return if (remoteShifts != null) {
            shiftDao.deleteShiftsForMonth(monthPrefix)
            shiftDao.upsertShifts(remoteShifts)
            remoteShifts
        } else {
            cachedShifts
        }
    }

    override suspend fun getShiftDefinitionsForMonth(shiftCodes: List<String>): List<ShiftDefinition> {
        if (shiftCodes.isEmpty()) {
            return emptyList()
        }

        val cachedDefinitions = shiftDefinitionDao
            .getShiftDefinitionsForCodes(shiftCodes)
            .map { it.toDomain() }

        val remoteDefinitions = withTimeoutOrNull(2_000L.milliseconds) {
            try {
                firestoreShiftDataSource.getShiftDefinitionsForMonth(shiftCodes)
            } catch (exception: Exception) {
                Log.e(
                    logcatTag,
                    "Failed to refresh shift definitions from Firestore, using cached Room data",
                    exception
                )
                null
            }
        }

        return if (remoteDefinitions != null) {
            shiftDefinitionDao.upsertShiftDefinitions(remoteDefinitions.map { it.toEntity() })
            remoteDefinitions
        }  else {
            cachedDefinitions
        }
    }

    /**
     * @param monthPrefix the string month prefix with the year and month, in the following format: xxxx-xx. For example, 2026-05
     * @param shifts a list of [ShiftEntity], where each entity has a key value of year and month in xxxx-xx format, and value in the form of shift time xxxx.
     * For example, 2110 to represent start time of 9 pm and 10 hours long
     **/
    override suspend fun saveShiftsForMonth(monthPrefix: String, shifts: List<ShiftEntity>) {
        shiftDao.deleteShiftsForMonth(monthPrefix)
        shiftDao.upsertShifts(shifts)
    }

    /**
     * @param monthPrefix the string month prefix with the year and month, in the following format: xxxx-xx. For example, 2026-05
     */
    override suspend fun deleteShiftsForMonth(monthPrefix: String) {
        shiftDao.deleteShiftsForMonth(monthPrefix)
    }
}