package com.jacobdgraham.myschedule.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacobdgraham.myschedule.data.repository.ShiftRepository
import com.jacobdgraham.myschedule.domain.model.MonthSchedule
import com.jacobdgraham.myschedule.domain.model.ShiftDay
import com.jacobdgraham.myschedule.ui.state.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth

/**
 * [CalendarViewModel] updates current [CalendarUiState] to display current data on the main application window calendar.
 * Below are the things this class is responsible for:
 * ```
 * 1. Loading the selected month shift data either from firebase or local Room database
 * 2. Converting that stored shift entity data into calendar days
 * 3. Loading the converted shift definitions used in that month
 * ```
 *
 * Data flow:
 * ```
 * 1. User selects month to load data from the ui
 * 2. CalendarViewModel asks ShiftRepository for the data containing shifts in this month
 * 3. CalendarViewModel extracts shift codes from that data used in the selected month
 * 4. CalendarViewModel asks ShiftRepository for the matching shift definitions for that particular shift
 * 5. CalendarUiState is updated so the calendar grid and shift legend updated to contain data only relevant for that month
 * ```
 *
 * @property shiftRepository repository that is used to load cached (Room) and/or remote (Firestore) schedule data
 */
class CalendarViewModel(private val shiftRepository: ShiftRepository): ViewModel() {

    private val logcatTag: String = "CalendarViewModel"

    private val _uiState = MutableStateFlow(
        CalendarUiState(
            selectedMonth = YearMonth.now(),
            monthSchedule = null,
            isLoading = true,
            errorMessage = null
        )
    )

    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        loadMonth(YearMonth.of(2026, 6))
    }


    /**
     * Loads schedule data for [yearMonth] and updates [uiState]
     *
     * Builds a full [MonthSchedule] containing every day in the selected month. All days are accounted for, but if they were not worked, no value is
     * inserted into the cell, and is null
     *
     * Shift legend is generated dynamically by looking at the shift codes used in that month, and loads only definitions for those shift codes
     *
     * @param yearMonth the month to load
     */
    fun loadMonth(yearMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                selectedMonth = yearMonth,
                isLoading = true,
                hasAttemptedLoad = false,
                errorMessage = null
            )

            try {
                val shiftEntities = shiftRepository.getShiftsForMonth(yearMonth)

                val shiftsByDate = shiftEntities.associateBy { shiftEntity ->
                    shiftEntity.date
                }

                val days = (1..yearMonth.lengthOfMonth()).map { day ->
                    val date = yearMonth.atDay(day).toString()

                    ShiftDay(
                        date = date,
                        shiftCode = shiftsByDate[date]?.shiftCode
                    )
                }

                val usedShiftCodes = shiftEntities
                    .map {it.shiftCode}
                    .distinct()
                    .sorted()

                val monthSchedule = MonthSchedule(
                    year = yearMonth.year,
                    month = yearMonth.monthValue,
                    days = days
                )

                val shiftDefinitions = shiftRepository.getShiftDefinitionsForMonth(usedShiftCodes)

                _uiState.value = CalendarUiState(
                    selectedMonth = yearMonth,
                    monthSchedule = monthSchedule,
                    shiftDefinitions = shiftDefinitions,
                    isLoading = false,
                    hasAttemptedLoad = true,
                    errorMessage = null
                )
            } catch (exception: Exception) {
                Log.e(
                    logcatTag,
                    "Failed to load schedule for month: $yearMonth",
                    exception
                )
                _uiState.value = _uiState.value.copy(
                    selectedMonth = yearMonth,
                    shiftDefinitions = emptyList(),
                    isLoading = false,
                    hasAttemptedLoad = true,
                    errorMessage = "Could not load schedule for this month"
                )
            }
        }
    }
}