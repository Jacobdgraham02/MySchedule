package com.jacobdgraham.myschedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacobdgraham.myschedule.data.repository.ShiftRepository

/**
 * A FACTORY!!! EVERYONE's FAVOURITE :))))
 *
 * Factory used to create [CalendarViewModel] with constructor dependencies
 * To customize our Android view model, we must create this, as implementation of [CalendarViewModel] requires a [ShiftRepository] parameter
 *
 * @property shiftRepository repository passed into any created [CalendarViewModel] instances
 */
class CalendarViewModelFactory(
    private val shiftRepository: ShiftRepository
) : ViewModelProvider.Factory {

    /**
     * Creates a [CalendarViewModel] instance when requested
     * @throws IllegalArgumentException if a ViewModel class other than [CalendarViewModel] is requested
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(shiftRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}