package com.jacobdgraham.myschedule.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local Room entity used to cache shift code definitions
 * Allows the app to display the shift legend when offline as long as the data was loaded previously from Firestore at least once
 * While [ShiftEntity] represents a shift worked on a specific date, this entity represents the meaning of that specific shift code
 * Example:
 * code = "2010"
 * definition = "8:00 p.m. to 6:00 a.m."
 *
 * @property code shift code to represent a shift worked on that day
 * @property definition human readable meaning of the shift code
 */
@Entity(tableName="shift_definitions")
data class ShiftDefinitionEntity (
    @PrimaryKey val code: String,
    val definition: String
)