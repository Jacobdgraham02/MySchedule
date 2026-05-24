package com.jacobdgraham.myschedule.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="shifts")
data class ShiftEntity(
    @PrimaryKey val date: String,
    val shiftCode: String
)