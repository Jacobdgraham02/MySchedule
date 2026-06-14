package com.jacobdgraham.myschedule.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO for cached shift definitions, reading from and writing to the local Room database
 * App only loads definitions for shift codes used in the selected month. If the selected month only has a 0610 shift, this DAO will only
 * return definitions for 0610 for that month
 */
@Dao
interface IShiftDefinitionDao {
    /**
     * Returns cached shift definitions matching the supplied shift codes.
     *
     * This is used when building the dynamic shift legend for the selected month
     *
     * @param shiftCodes shift codes used in the selected month.
     * @return cached definitions for the requested codes, sorted by code
     */
    @Query("SELECT * FROM shift_definitions WHERE code IN (:shiftCodes) ORDER BY code ASC")
    suspend fun getShiftDefinitionsForCodes(shiftCodes: List<String>): List<ShiftDefinitionEntity>

    /**
     * Inserts or updates shift definitions in the Room cache
     *
     * This is called after definitions are successfully loaded from Firestore. Existing
     * definitions with the same code are replaced so local data stays up to date
     *
     * @param shiftDefinitions definitions to save locally
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertShiftDefinitions(shiftDefinitions: List<ShiftDefinitionEntity>)
}