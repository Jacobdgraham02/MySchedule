package com.jacobdgraham.myschedule.data.repository.interfaces

import com.jacobdgraham.myschedule.data.local.ShiftEntity

interface IShiftRepository {
    suspend fun getShiftsForMonth(monthPrefix: String): List<ShiftEntity>
    suspend fun saveShiftsForMonth(monthPrefix: String, shifts: List<ShiftEntity>)
    suspend fun deleteShiftsForMonth(monthPrefix: String)
}