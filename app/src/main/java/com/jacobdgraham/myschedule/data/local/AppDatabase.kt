package com.jacobdgraham.myschedule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Declaration for local Room database which defines which entities exist in the database and which DAOs are available
 */
@Database(
    entities = [ShiftEntity::class, ShiftDefinitionEntity::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun shiftDao(): IShiftDao
    abstract fun shiftDefinitions(): IShiftDefinitionDao
}