package com.jacobdgraham.myschedule.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="shift_definitions")
data class ShiftDefinitionEntity (
    @PrimaryKey val code: String,
    val definition: String
)