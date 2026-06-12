package com.jacobdgraham.myschedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacobdgraham.myschedule.data.repository.ShiftRepository

class CalendarViewModelFactory(
    private val shiftRepository: ShiftRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(shiftRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}