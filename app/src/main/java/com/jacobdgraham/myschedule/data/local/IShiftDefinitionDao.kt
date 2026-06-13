package com.jacobdgraham.myschedule.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IShiftDefinitionDao {
    @Query("SELECT * FROM shift_definitions WHERE code IN (:shiftCodes) ORDER BY code ASC")
    suspend fun getShiftDefinitionsForCodes(shiftCodes: List<String>): List<ShiftDefinitionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertShiftDefinitions(shiftDefinitions: List<ShiftDefinitionEntity>)
}