package com.jacobdgraham.myschedule.data.repository.interfaces

import com.jacobdgraham.myschedule.data.local.ShiftEntity
import java.time.YearMonth

interface IShiftRepository {
    suspend fun getShiftsForMonth(yearMonth: YearMonth): List<ShiftEntity>
//    suspend fun saveShiftsForMonth(monthPrefix: String, shifts: List<ShiftEntity>)
//    suspend fun deleteShiftsForMonth(monthPrefix: String)
}