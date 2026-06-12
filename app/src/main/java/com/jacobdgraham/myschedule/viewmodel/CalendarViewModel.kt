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


     fun loadMonth(yearMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                selectedMonth = yearMonth,
                isLoading = true,
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

                val monthSchedule = MonthSchedule(
                    year = yearMonth.year,
                    month = yearMonth.monthValue,
                    days = days
                )

                _uiState.value = CalendarUiState(
                    selectedMonth = yearMonth,
                    monthSchedule = monthSchedule,
                    isLoading = false,
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
                    isLoading = false,
                    errorMessage = "Could not load schedule for this month"
                )
            }
        }
    }
}